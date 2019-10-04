package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimStatus;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewTravelClaimReceiptsRequestHandler extends RequestHandlerImpl {

	public ViewTravelClaimReceiptsRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		int id = -1;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				if (usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID()) {

					request.setAttribute("SUPERVISOR", "true");
					if (claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED)
							|| claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)) {
						claim.setCurrentStatus(usr.getPersonnel(), TravelClaimStatus.REVIEWED);
					}
					request.setAttribute("TRAVELCLAIM", claim);
					path = "claim_receipts.jsp";
				}
				else if (usr.getPersonnel().getPersonnelID() == claim.getPersonnel().getPersonnelID()) {
					request.setAttribute("TRAVELCLAIM", claim);
					path = "claim_receipts.jsp";
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