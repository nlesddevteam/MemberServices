package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteTravelClaimItemRequestHandler extends RequestHandlerImpl {

	public DeleteTravelClaimItemRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		TravelClaimItem item = null;
		String op = "";
		int cid = -1, iid = -1;
		boolean check = false;

		try {
			cid = Integer.parseInt(request.getParameter("cid"));
		}
		catch (NumberFormatException e) {
			cid = -1;
		}

		try {
			iid = Integer.parseInt(request.getParameter("iid"));
		}
		catch (NumberFormatException e) {
			iid = -1;
		}

		if (cid < 1) {
			throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR DELETE ITEM OPERATION.  >>>>>");
		}
		else if (iid < 1) {
			throw new TravelClaimException("<<<<< ITEM ID IS REQUIRED FOR DELETE ITEM OPERATION.  >>>>>");
		}
		else {
			claim = TravelClaimDB.getClaim(cid);
			request.setAttribute("TRAVELCLAIM", claim);

			item = TravelClaimItemsDB.getClaimItem(iid);
			request.setAttribute("TRAVELCLAIMITEM", item);

			if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
				request.setAttribute("msg", "You do not have permission to perform delete operation.");
				request.setAttribute("NOPERMISSION", new Boolean(true));
			}
			else {
				op = request.getParameter("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						check = TravelClaimItemsDB.deleteClaimItem(iid);

						if (check) {
							request.setAttribute("RESULT", "SUCCESS");
							request.setAttribute("msg", "Item deleted successfully.");
						}
						else {
							request.setAttribute("RESULT", "FAILED");
							request.setAttribute("msg", "Item could not be deleted.");
						}
					}
					else {
						request.setAttribute("msg", "Invalid operation.");
					}
				}
			}
		}

		path = "delete_claim_item.jsp";

		return path;
	}
}