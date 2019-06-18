package com.awsd.admin.apps.personnel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddSchoolRequestHandler extends RequestHandlerImpl {

	public AddSchoolRequestHandler() {

		requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("sname")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		School s = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			s = new School();
			s.setSchoolName(form.get("sname"));

			if (form.exists("deptid"))
				s.setSchoolDeptID(form.getInt("deptid"));

			if (form.exists("pid") && !form.hasValue("pid", "-1"))
				s.setSchoolPrincipal(PersonnelDB.getPersonnel(form.getInt("pid")));

			if (form.equals("vid"))
				s.setAssistantPrincipals(form.getArray("vid"));

			try {
				s.save();
				request.setAttribute("msg", s.getSchoolName() + " has been add to database.");
			}
			catch (SchoolException e) {
				e.printStackTrace(System.err);

				request.setAttribute("msg", "Could not add " + s.getSchoolName() + " to database due to exception!");
			}
		}
		else
			request.setAttribute("msg", validator.getErrorString());

		path = "school_admin_view.jsp";

		return path;
	}
}