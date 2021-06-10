package com.awsd.admin.apps.personnel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.User;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class MSLoginAsRequestHandler extends RequestHandlerImpl {

	public MSLoginAsRequestHandler() {

		requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("pid")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			Personnel p = PersonnelDB.getPersonnel(form.getInt("pid"));

			session.setAttribute("ADMIN_USR", usr);

			usr = new User(p.getUserName(), p.getPassword());

			session.setAttribute("usr", usr);
			session.setAttribute("ADMIN_LOGIN_AS", new Boolean(true));

			path = "/memberservices_frame.jsp";
		}
		else {
			request.setAttribute("msg", validator.getErrorString());

			path = "/MemberAdmin/memberadmin.jsp";
		}

		return path;
	}
}