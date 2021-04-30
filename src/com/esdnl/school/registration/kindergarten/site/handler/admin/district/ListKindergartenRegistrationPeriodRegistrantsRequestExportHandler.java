package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListKindergartenRegistrationPeriodRegistrantsRequestExportHandler extends RequestHandlerImpl {

	public ListKindergartenRegistrationPeriodRegistrantsRequestExportHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("krp")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			KindergartenRegistrationPeriodBean period = KindergartenRegistrationManager.getKindergartenRegistrationPeriodBean(form.getInt("krp"));

			request.setAttribute("krp", period);
			request.setAttribute("registrants", KindergartenRegistrationManager.getKindergartenRegistrantBeans(period));

			session.setAttribute("ReturnURL", request.getRequestURI() + "?krp=" + form.get("krp"));
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msg", e.getMessage());
		}

		this.path = "district_period_export.jsp";

		return this.path;
	}
}
