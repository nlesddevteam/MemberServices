package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class SaveTravelClaimChangesRequestHandler extends RequestHandlerImpl {

	public SaveTravelClaimChangesRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		TravelClaimItem item = null;
		Iterator iter = null;
		int id = -1;
		boolean done = false;

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
						iter = claim.getItems().iterator();
						while (iter.hasNext()) {
							item = (TravelClaimItem) iter.next();
							if (request.getParameter("item_" + item.getItemID()) != null) {
								try {
									item.setItemOther(
											Double.parseDouble(request.getParameter("item_" + item.getItemID()).replaceAll("[$,]", "")));
									done = TravelClaimItemsDB.updateClaimItem(claim, usr.getPersonnel(), item);
								}
								catch (Exception e) {
									e.printStackTrace(System.err);
									done = true;
								}
							}
						}

						if (!done)
							request.setAttribute("msg", "Some changes could not be saved...");
						else
							request.setAttribute("msg", "Changes saved successfully...");

						request.setAttribute("TOTAL_CLAIMED",
								new Double(TravelClaimDB.getYearToDateTotalClaimed(claim.getPersonnel(), claim.getFiscalYear())));
						request.setAttribute("BUDGET",
								TravelBudgetService.getTravelBudget(claim.getPersonnel(), claim.getFiscalYear()));
						path = "claim_details.jsp";
					}
					else
						path = "save_claim_changes.jsp";
				}
				else
					path = "save_claim_changes.jsp";
			}
			else
				path = "claim_error.jsp?msg=Claim cound not be found.";
		}
		else
			path = "claim_error.jsp?msg=Claim ID Required For SAVE CHANGES Operation.";
		return path;
	}
}