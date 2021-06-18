package com.nlesd.school.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.servlet.RequestHandlerImpl;

public class MSAddSchoolRequestHandler extends RequestHandlerImpl {

	public MSAddSchoolRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			request.setAttribute("school", new School());

			path = "/SchoolDirectory/school_profile_add.jsp";
		}
		else {
			path = "/SchoolDirectory/school_profile.jsp";

			request.setAttribute("msgERR", "ERROR: Could not initiate the add school process.");
		}

		return path;
	}

}
