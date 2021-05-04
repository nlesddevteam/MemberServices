package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ApprovePhysicalAddressKindergartenRegistrantRequestHandler extends RequestHandlerImpl {

	public ApprovePhysicalAddressKindergartenRegistrantRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("kr")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			KindergartenRegistrantBean kr = KindergartenRegistrationManager.getKindergartenRegistrantBean(form.getInt("kr"));

			kr = KindergartenRegistrationManager.approvePhysicalAddress(kr);
			request.setAttribute("msgOK", "SUCCESS: Address and MCP has been set to APPROVED.");
			request.setAttribute("kr", kr);
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msgERR", e.getMessage());
		}

		this.path = "district_view_registrant.jsp";

		return this.path;
	}
}
