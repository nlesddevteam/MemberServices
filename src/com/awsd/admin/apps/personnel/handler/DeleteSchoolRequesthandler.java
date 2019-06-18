package com.awsd.admin.apps.personnel.handler;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteSchoolRequesthandler extends RequestHandlerImpl {

	public DeleteSchoolRequesthandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("sid", "School ID Required.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		School s = null;
		Vector<Personnel> teachers = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			s = SchoolDB.getSchool(form.getInt("sid"));

			teachers = PersonnelDB.getPersonnelList(s);

			int cnt = teachers.size();

			if (teachers.size() > 0) {
				for (Personnel tmp : teachers) {
					tmp.setSchool(null);
					PersonnelDB.updatePersonnel(tmp);
				}

				teachers = PersonnelDB.getPersonnelList(s);
			}

			if (teachers.size() <= 0) {
				if (!SchoolDB.deleteSchool(s)) {
					request.setAttribute("msg", "Could not delete " + s.getSchoolName() + " from database!");
				}
				else {
					request.setAttribute("msg", s.getSchoolName() + " had " + cnt
							+ " teacher(s) reassigned. Deletion successful.");
				}
			}
			else {
				request.setAttribute("msg", s.getSchoolName() + " has " + teachers.size()
						+ " teacher(s) associated with it. Delete could not be performed!");
			}
		}
		else {
			request.setAttribute("msg", this.validator.getErrorString());
		}

		path = "school_directory.jsp";

		return path;
	}
}