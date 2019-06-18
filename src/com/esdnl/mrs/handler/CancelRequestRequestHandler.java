package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class CancelRequestRequestHandler extends RequestHandlerImpl {

	public CancelRequestRequestHandler() {

		this.requiredPermissions = new String[] {
			"MAINTENANCE-SCHOOL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("req", "REQUEST ID is require to CANCEL")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			if (request.getParameter("complete") != null && request.getParameter("complete").equals("1"))
				MaintenanceRequestDB.updateMaintenanceRequestStatus(Integer.parseInt(request.getParameter("req")), "COMPLETED");
			else
				MaintenanceRequestDB.updateMaintenanceRequestStatus(Integer.parseInt(request.getParameter("req")), "CANCELLED");
		}
		else
			request.setAttribute("msg", this.validator.getErrorString());

		session.setAttribute("OUTSTANDING_REQUESTS",
				MaintenanceRequestDB.getOutstandingMaintenanceRequests(usr.getPersonnel().getSchool()));

		path = "school_outstanding_requests.jsp";

		return path;
	}
}