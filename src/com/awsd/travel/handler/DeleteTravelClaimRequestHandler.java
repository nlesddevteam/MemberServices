package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteTravelClaimRequestHandler extends RequestHandlerImpl {

	public DeleteTravelClaimRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		TravelClaim claim = null;
		String op = "";
		int id = -1;
		boolean check = false;
		boolean isAdmin = false;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id < 1) {
			throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR DELETE OPERATION.  >>>>>");
		}
		else {
			isAdmin = usr.getUserRoles().containsKey("ADMINISTRATOR");

			claim = TravelClaimDB.getClaim(id);
			if (claim instanceof PDTravelClaim)
				((PDTravelClaim) claim).getPD();
			request.setAttribute("TRAVELCLAIM", claim);

			if (!isAdmin && (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID())) {
				request.setAttribute("msg", "You do not have permission to delete this travel claim.");
				request.setAttribute("NOPERMISSION", new Boolean(true));
			}
			else {
				op = request.getParameter("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						check = TravelClaimDB.deleteClaim(claim);

						if (check) {
							request.setAttribute("RESULT", "SUCCESS");
							request.setAttribute("msg", "Claim deleted successfully.");
						}
						else {
							request.setAttribute("RESULT", "FAILED");
							request.setAttribute("msg", "Claim could not be deleted.");
						}
					}
					else {
						request.setAttribute("msg", "Invalid operation.");
					}
				}
			}
		}

		path = "delete_claim.jsp";

		return path;
	}
}