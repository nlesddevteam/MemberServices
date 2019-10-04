package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimNote;
import com.awsd.travel.TravelClaimNoteDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelClaimNoteRequestHandler extends RequestHandlerImpl {

	public AddTravelClaimNoteRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-SUPERVISOR-VIEW", "TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"
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
				request.setAttribute("TRAVELCLAIM", claim);

				if (request.getParameter("op") != null) {
					if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
						if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
							if (TravelClaimNoteDB.addClaimNote(claim,
									new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")))) {
								request.setAttribute("msg", "Note Added Successfully.");
							}
							else {
								request.setAttribute("msg", "Note could not be added.");
							}
						}
						else {
							request.setAttribute("msg", "Note cannot be empty.");
						}
					}
				}
				path = "add_claim_note.jsp";
			}
			else {
				path = "claim_error.jsp?msg=Claim cound not be found.";
			}
		}
		else
			path = "claim_error.jsp?msg=Claim ID Required For ADD Note Operation.";

		return path;
	}
}