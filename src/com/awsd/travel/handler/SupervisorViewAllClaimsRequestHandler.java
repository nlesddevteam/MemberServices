package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class SupervisorViewAllClaimsRequestHandler extends RequestHandlerImpl {

	public SupervisorViewAllClaimsRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW", "TRAVEL-CLAIM-SUPERVISOR-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		Personnel p = null;
		int id = -1;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			p = PersonnelDB.getPersonnel(id);

			if (p != null) {
				request.setAttribute("PERSONNEL", p);
				path = "all_claims_view.jsp";
			}
			else {
				path = "claim_error.jsp?msg=employee cound not be found.";
			}
		}
		else {
			path = "claim_error.jsp?msg=Personnel ID is required to view all employee claims.";
		}

		return path;
	}
}