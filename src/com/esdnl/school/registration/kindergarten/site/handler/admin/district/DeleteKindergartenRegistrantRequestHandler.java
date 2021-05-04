package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteKindergartenRegistrantRequestHandler extends RequestHandlerImpl {

	public DeleteKindergartenRegistrantRequestHandler() {

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

			KindergartenRegistrationPeriodBean period = kr.getRegistration();

			KindergartenRegistrationManager.deleteKindergartenRegistrantBean(kr);

			//request.setAttribute("krp", period);
			request.setAttribute("registrants", KindergartenRegistrationManager.getKindergartenRegistrantBeans(period));
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msg", e.getMessage());
		}
		 //this.path = (String) session.getAttribute("ReturnURL");
		 	session.setAttribute("ReturnURL", request.getRequestURI() + "?krp=" + form.get("krp"));	      
		 
	        this.path = "district_period_registrants.jsp";	 
	        
	        return this.path;
	}
}
