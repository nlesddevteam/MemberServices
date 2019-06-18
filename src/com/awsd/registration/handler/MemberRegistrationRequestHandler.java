package com.awsd.registration.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelCategory;
import com.awsd.personnel.PersonnelCategoryDB;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.registration.RegistrationException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class MemberRegistrationRequestHandler extends PublicAccessRequestHandlerImpl {

	public MemberRegistrationRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("firstname", "First name required for registration."),
				new RequiredFormElement("lastname", "Last name required for registration."),
				new RequiredFormElement("emailaddr", "Email required for registration."),
				new RequiredFormElement("job", "Job function required for registration."),
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		School s = null;
		int sid, supervisorid = 0;
		Map<Integer, Personnel> teachers = null;

		super.handleRequest(request, response);

		if (validate_form()) {

			try {
				//verify that email address is not already in use.
				try {
					if (PersonnelDB.getPersonnelByEmail(form.get("emailaddr")) != null) {
						throw new RegistrationException("Account already exists using the username and/or email provided.");
					}
				}
				catch (PersonnelException e) {
					// email not found - GOOD!
				}
				catch (SQLException e) {
					throw new RegistrationException("An error occurred during the registration process. Please try again.");
				}

				//verify school is provided for appropriate categories.
				PersonnelCategory cat = PersonnelCategoryDB.getPersonnelCategory(form.getInt("job"));

				if (cat.getPersonnelCategoryName().equalsIgnoreCase("TEACHER")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("PRINCIPAL")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("VICE PRINCIPAL")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("SCHOOL SECRETARY")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("SCHOOL MAINTENANCE")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("GUIDANCE COUNSELLOR")) {
					if (!form.exists("school")) {
						throw new RegistrationException("School required for registration.");
					}
					else {
						s = SchoolDB.getSchool(form.getInt("school"));
						if (s == null) {
							throw new RegistrationException("Error: could not find school");
						}
						else if (cat.getPersonnelCategoryName().equalsIgnoreCase("PRINCIPAL") && (s.getSchoolPrincipal() != null)) {
							throw new RegistrationException(s.getSchoolPrincipal().getFullNameReverse()
									+ " has has already been assigned as principal for " + s.getSchoolName()
									+ ". Please ensure you have choosen the correct category, and contact tech support if necessary.");
						}
						else {
							sid = s.getSchoolID();
						}
					}
				}
				else {
					sid = 0;
				}

				if (cat.getPersonnelCategoryName().equalsIgnoreCase("TEACHER")
						|| cat.getPersonnelCategoryName().equalsIgnoreCase("VICE PRINCIPAL")) {
					if (s.getSchoolPrincipal() != null) {
						supervisorid = s.getSchoolPrincipal().getPersonnelID();
					}
					else {
						supervisorid = 0;
					}
				}

				Personnel p = new Personnel(form.get("emailaddr").toLowerCase(), null, form.get("firstname"), form.get("lastname"), form.get("emailaddr"), cat.getPersonnelCategoryID(), supervisorid, sid);

				int id = PersonnelDB.addPersonnel(p);

				p.setPersonnelID(id);
				p.setPersonnelCategory(cat);

				if (cat.getPersonnelCategoryName().equalsIgnoreCase("PRINCIPAL")) {
					try {
						s.setSchoolPrincipal(p);
						s.save();

						teachers = PersonnelDB.getPersonnel(s);

						for (Personnel tmp : teachers.values()) {
							if (tmp.getPersonnelID() != p.getPersonnelID()) {
								tmp.setSupervisor(p);
							}
						}
					}
					catch (Exception e) {
						throw new RegistrationException("Could not find school");
					}
				}

				if (cat.getPersonnelCategoryName().equalsIgnoreCase("VICE PRINCIPAL")) {
					try {
						//s.setSchoolVicePrincipal(p);
						s.addAssistantPrincipal(p);
						s.save();
					}
					catch (SchoolException e) {
						throw new RegistrationException("Could not find school");
					}
				}

				if (usr != null && usr.isAdmin()) {
					request.setAttribute("msg", "Account added successfully.");

					path = "register.jsp";
				}
				else {
					//usr.setPersonnel(p);
					//session.setAttribute("usr", usr);

					path = "signin.jsp";
				}

				System.err.println("NEW REGISTRATION [ " + id + " : " + form.get("emailaddr") + "]");
			}
			catch (RegistrationException e) {
				request.setAttribute("msg", e.getMessage());
				request.setAttribute("form", form);

				path = "register.jsp";
			}
		}
		else {
			request.setAttribute("msg", this.validator.getErrorString());
			request.setAttribute("form", form);

			path = "register.jsp";
		}

		return path;
	}
}