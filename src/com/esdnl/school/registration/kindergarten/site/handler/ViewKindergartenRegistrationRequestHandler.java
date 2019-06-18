package com.esdnl.school.registration.kindergarten.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;

public class ViewKindergartenRegistrationRequestHandler extends PublicAccessRequestHandlerImpl {

	public ViewKindergartenRegistrationRequestHandler() {

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			KindergartenRegistrationPeriodBean ap = KindergartenRegistrationManager.getActiveKindergartenRegistrationPeriodBean();
			request.setAttribute("ap", ap);

			if (ap == null) {
				request.setAttribute("fp", KindergartenRegistrationManager.getFutureKindergartenRegistrationPeriodBean());
			}

			if (form.exists("rel"))
				request.setAttribute("sibling",
						KindergartenRegistrationManager.getKindergartenRegistrantBean(form.getInt("rel")));
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("ap", null);
		}

		this.path = "registration_index.jsp";

		return this.path;
	}

}
