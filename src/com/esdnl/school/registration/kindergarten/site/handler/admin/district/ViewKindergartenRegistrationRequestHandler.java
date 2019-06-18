package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewKindergartenRegistrationRequestHandler extends RequestHandlerImpl {

	public ViewKindergartenRegistrationRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			request.setAttribute("periods", KindergartenRegistrationManager.getKindergartenRegistrationPeriodBeans());
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msg", e.getMessage());
		}

		this.path = "district_index.jsp";

		return this.path;
	}
}
