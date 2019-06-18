package com.esdnl.school.registration.kindergarten.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.school.service.SchoolZoneService;

public class ViewKindergartenRegistrationRequestHandler extends RequestHandlerImpl {

	public ViewKindergartenRegistrationRequestHandler() {

		this.requiredPermissions = new String[] {
				"KINDERGARTEN-REGISTRATION-ADMIN-VIEW", "KINDERGARTEN-REGISTRATION-SCHOOL-VIEW",
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (usr.checkPermission("KINDERGARTEN-REGISTRATION-ADMIN-VIEW")) {
			request.setAttribute("zones", SchoolZoneService.getSchoolZoneBeans());

			this.path = "district/index.html";
		}
		else if (usr.checkPermission("KINDERGARTEN-REGISTRATION-SCHOOL-VIEW")) {
			this.path = "school/index.html";
		}

		return this.path;
	}

}
