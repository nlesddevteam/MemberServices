package com.awsd.travel.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class EditTravelClaimItemRequestHandler extends RequestHandlerImpl {

	public EditTravelClaimItemRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		TravelClaimItem item = null, item_old = null;
		SimpleDateFormat sdf = null;
		String op = "";
		int cid = -1, iid = -1;
		boolean check = false;
		Calendar date_chk = null;

		try {
			cid = Integer.parseInt(request.getParameter("id"));
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
			throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR EDIT ITEM OPERATION.  >>>>>");
		}
		else if (iid < 1) {
			throw new TravelClaimException("<<<<< ITEM ID IS REQUIRED FOR EDIT ITEM OPERATION.  >>>>>");
		}
		else {
			claim = TravelClaimDB.getClaim(cid);

			if (claim != null) {
				if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
					request.setAttribute("msg", "You do not have permission to edit an item of this travel claim.");
					request.setAttribute("NOPERMISSION", new Boolean(true));
					path = "claim_error.jsp";
				}
				else {
					item_old = TravelClaimItemsDB.getClaimItem(iid);

					if (item_old != null) {
						request.setAttribute("TRAVELCLAIM", claim);

						op = request.getParameter("op");
						if (op != null) {
							if (op.equalsIgnoreCase("CONFIRM")) {
								sdf = new SimpleDateFormat("dd/MM/yyyy");
								try {
									item = new TravelClaimItem(sdf.parse(request.getParameter("item_date")), request.getParameter(
											"item_desc"), Integer.parseInt(request.getParameter("item_kms")), Double.parseDouble(
													request.getParameter("item_meals")), Double.parseDouble(
															request.getParameter("item_lodging")), Double.parseDouble(
																	request.getParameter("item_other")), request.getParameter(
																			"item_departure_time"), request.getParameter("item_return_time"));
								}
								catch (ParseException e) {
									System.err.println(e);
									item = null;
								}

								if (item != null) {
									date_chk = Calendar.getInstance();
									date_chk.setTime(item.getItemDate());

									//check item date
									if ((!claim.getFiscalYear().equalsIgnoreCase(Utils.getSchoolYear(date_chk)))
											|| (claim.getFiscalMonth() != date_chk.get(Calendar.MONTH))) {
										request.setAttribute("RESULT", "FAILED");
										request.setAttribute("msg",
												"Claim item date is invalid.<br>Only items for " + Utils.getMonthString(claim.getFiscalMonth())
														+ " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " are valid.");

										item.setItemID(item_old.getItemID());
										request.setAttribute("FAILED_ITEM", item);
										request.setAttribute("EDIT", new Boolean(true));
									}
									else if (Utils.compareCurrentDate(item.getItemDate()) >= 1) {
										request.setAttribute("RESULT", "FAILED");
										request.setAttribute("msg",
												"Claim item date is in the future.<br>Only items on or before "
														+ (new SimpleDateFormat("MMMM dd, yyyy").format((Calendar.getInstance()).getTime()))
														+ " are valid.");

										item.setItemID(item_old.getItemID());
										request.setAttribute("FAILED_ITEM", item);
										request.setAttribute("EDIT", new Boolean(true));
									}
									else if ((item.getItemKMS() == 0) && (item.getItemMeals() == 0) && (item.getItemLodging() == 0)
											&& (item.getItemOther() == 0)) {
										request.setAttribute("RESULT", "FAILED");
										request.setAttribute("msg",
												"All claim item values are zero (0).<br> Please enter one or more values greater then zero (0).");

										item.setItemID(item_old.getItemID());
										request.setAttribute("FAILED_ITEM", item);
										request.setAttribute("EDIT", new Boolean(true));
									}
									else {
										check = TravelClaimItemsDB.editClaimItem(claim, item_old, item);

										if (check) {
											request.setAttribute("RESULT", "SUCCESS");
											request.setAttribute("msg", "Claim item edited successfully.");
										}
										else {
											request.setAttribute("RESULT", "FAILED");
											request.setAttribute("msg", "Claim item could not be edited.");
										}
									}
								}
								else {
									request.setAttribute("RESULT", "FAILED");
									request.setAttribute("msg", "Claim item could not be edited.");
								}

							}
							else if (op.equalsIgnoreCase("CANCEL"))
								; // do nothing

							else
								request.setAttribute("msg", "Invalid operation.");
						}
						else {
							request.setAttribute("FAILED_ITEM", item_old);
							request.setAttribute("EDIT", new Boolean(true));
						}

						request.setAttribute("TOTAL_CLAIMED",
								new Double(TravelClaimDB.getYearToDateTotalClaimed(claim.getPersonnel(), claim.getFiscalYear())));

						path = "claim_details.jsp";
					}
					else
						path = "claim_error.jsp?msg=Claim Item cound not be found.";
				}
			}
			else
				path = "claim_error.jsp?msg=Claim cound not be found.";
		}

		return path;
	}
}