package com.nlesd.school.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddSchoolRequestHandler extends RequestHandlerImpl {

	public AddSchoolRequestHandler() {

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

			path = "school_profile_edit.jsp";
		}
		else {
			path = "school_profile.jsp";

			request.setAttribute("msg", "Could not initiate the add school process.");
		}

		return path;
	}

}
