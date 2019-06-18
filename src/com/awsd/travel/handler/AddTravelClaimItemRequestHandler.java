package com.awsd.travel.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.common.Utils;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.awsd.travel.service.TravelBudgetService;

public class AddTravelClaimItemRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		TravelClaim claim = null;
		TravelClaimItem item = null;
		SimpleDateFormat sdf = null;
		String op = "";
		int id = -1;
		boolean check = false;
		Calendar date_chk = null;

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

		if (id < 1) {
			throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR CLAIM ITEM ADD OPERATION.  >>>>>");
		}
		else {
			claim = TravelClaimDB.getClaim(id);

			if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
				request.setAttribute("msg", "You do not have permission to add an item to this travel claim.");
				request.setAttribute("NOPERMISSION", new Boolean(true));
				path = "claim_error.jsp";
			}
			else {
				request.setAttribute("TRAVELCLAIM", claim);

				op = request.getParameter("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						sdf = new SimpleDateFormat("dd/MM/yyyy");
						try {
							System.out.println(request.getParameter("item_lodging"));
							System.out.println(Double.parseDouble(request.getParameter("item_lodging")));

							item = new TravelClaimItem(sdf.parse(request.getParameter("item_date")), request.getParameter("item_desc"), Integer.parseInt(request.getParameter("item_kms")), Double.parseDouble(request.getParameter("item_meals")), Double.parseDouble(request.getParameter("item_lodging")), Double.parseDouble(request.getParameter("item_other")), request.getParameter("item_departure_time"), request.getParameter("item_return_time"));

							System.out.println(item.getItemLodging());
						}
						catch (ParseException e) {
							System.err.println(e);
							item = null;
						}

						if (item != null) {
							date_chk = Calendar.getInstance();
							date_chk.setTime(item.getItemDate());

							// check item date
							if ((!claim.getFiscalYear().equalsIgnoreCase(Utils.getSchoolYear(date_chk)))
									|| (claim.getFiscalMonth() != date_chk.get(Calendar.MONTH))) {
								request.setAttribute("RESULT", "FAILED");
								request.setAttribute("msg", "Claim item date is invalid.<br>Only items for "
										+ Utils.getMonthString(claim.getFiscalMonth()) + " "
										+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " are valid.");

								request.setAttribute("FAILED_ITEM", item);
							}
							else if (Utils.compareCurrentDate(item.getItemDate()) >= 1) {
								request.setAttribute("RESULT", "FAILED");
								request.setAttribute("msg", "Claim item date is in the future.<br>Only items on or before "
										+ (new SimpleDateFormat("MMMM dd, yyyy").format((Calendar.getInstance()).getTime()))
										+ " are valid.");

								request.setAttribute("FAILED_ITEM", item);
							}
							else if ((item.getItemKMS() == 0) && (item.getItemMeals() == 0) && (item.getItemLodging() == 0)
									&& (item.getItemOther() == 0)) {
								request.setAttribute("RESULT", "FAILED");
								request.setAttribute("msg",
										"All claim item values are zero (0).<br> Please enter one or more values greater then zero (0).");

								request.setAttribute("FAILED_ITEM", item);
							}
							else {
								check = TravelClaimItemsDB.addClaimItem(claim, item);

								if (check) {
									request.setAttribute("RESULT", "SUCCESS");
									request.setAttribute("msg", "Claim item added successfully.");
								}
								else {
									request.setAttribute("RESULT", "FAILED");
									request.setAttribute("msg", "Claim item could not be added.");
								}
							}
						}
						else {
							request.setAttribute("RESULT", "FAILED");
							request.setAttribute("msg", "Claim item could not be added.");
						}
					}
					else {
						request.setAttribute("msg", "Invalid operation.");
					}
				}

				request.setAttribute("TOTAL_CLAIMED", new Double(TravelClaimDB.getYearToDateTotalClaimed(claim.getPersonnel(),
						claim.getFiscalYear())));
				request.setAttribute("BUDGET", TravelBudgetService.getTravelBudget(claim.getPersonnel(), claim.getFiscalYear()));

				path = "claim_details.jsp";
			}
		}

		return path;
	}
}