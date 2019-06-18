package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimStatus;

public class PrintTravelClaimDetailsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";

		TravelClaim claim = null;
		int id = -1;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				if ((usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID())
						|| ((claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)
								|| claim.getCurrentStatus().equals(TravelClaimStatus.PAYMENT_PENDING) || claim.getCurrentStatus().equals(
								TravelClaimStatus.PAID)) && (usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") || usr.getUserPermissions().containsKey(
								"TRAVEL-CLAIM-SEARCH")))) {

					request.setAttribute("SUPERVISOR", "true");
					if (claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED)
							|| claim.getCurrentStatus().equals(TravelClaimStatus.REVIEWED)) {
						claim.setCurrentStatus(usr.getPersonnel(), TravelClaimStatus.REVIEWED);
					}
					request.setAttribute("TRAVELCLAIM", claim);
					path = "print_claim.jsp";
				}
				else if (usr.getPersonnel().getPersonnelID() == claim.getPersonnel().getPersonnelID()) {
					request.setAttribute("TRAVELCLAIM", claim);
					path = "print_claim.jsp";
				}

				else {
					path = "claim_error.jsp?msg=You do not have permission to view this travel claim.";
				}
			}
			else {
				path = "claim_error.jsp?msg=Claim cound not be found.";
			}
		}
		else
			path = "claim_error.jsp?msg=Claim cound not be found.";

		return path;
	}
}