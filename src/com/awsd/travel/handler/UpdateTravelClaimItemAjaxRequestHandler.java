package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.security.User;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class UpdateTravelClaimItemAjaxRequestHandler extends RequestHandlerImpl {

	public UpdateTravelClaimItemAjaxRequestHandler() {

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
		SimpleDateFormat sdf = null;
		String op = "";
		int id = -1;
		Integer itemid = -1;
		boolean check = false;
		Calendar date_chk = null;
		boolean iserror = false;
		String errormessage = "";
		try {
			id = Integer.parseInt(request.getParameter("id"));
			itemid = Integer.parseInt(request.getParameter("itemid"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id < 1) {
			//throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR CLAIM ITEM ADD OPERATION.  >>>>>");
			//send xml back with error
			iserror = true;
			errormessage = "CLAIM ID IS REQUIRED FOR CLAIM ITEM ADD OPERATION";
		}
		else {
			claim = TravelClaimDB.getClaim(id);
			session = request.getSession(false);
			usr = (User) session.getAttribute("usr");
			if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
				iserror = true;
				errormessage = "You do not have permission to add an item to this travel claim";
			}
			else {
				request.setAttribute("TRAVELCLAIM", claim);
				op = request.getParameter("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						sdf = new SimpleDateFormat("MM/dd/yyyy");
						try {
							System.out.println(request.getParameter("item_lodging"));
							System.out.println(Double.parseDouble(request.getParameter("item_lodging")));
							item = new TravelClaimItem(sdf.parse(request.getParameter("item_date")), request.getParameter(
									"item_desc"), Integer.parseInt(request.getParameter("item_kms")), Double.parseDouble(
											request.getParameter("item_meals")), Double.parseDouble(
													request.getParameter("item_lodging")), Double.parseDouble(
															request.getParameter("item_other")), request.getParameter(
																	"item_departure_time"), request.getParameter("item_return_time"));
							System.out.println(item.getItemLodging());
						}
						catch (ParseException e) {
							System.err.println(e);
							iserror = true;
							errormessage = e.getMessage();
							item = null;
						}
						if (item != null) {
							date_chk = Calendar.getInstance();
							date_chk.setTime(item.getItemDate());
							// check item date
							if ((!claim.getFiscalYear().equalsIgnoreCase(Utils.getSchoolYear(date_chk)))
									|| (claim.getFiscalMonth() != date_chk.get(Calendar.MONTH))) {
								iserror = true;
								errormessage = "Claim item date is invalid.<br>Only items for "
										+ Utils.getMonthString(claim.getFiscalMonth()) + " "
										+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " are valid.";
							}
							else if (Utils.compareCurrentDate(item.getItemDate()) >= 1) {
								iserror = true;
								errormessage = "Claim item date is in the future.<br>Only items on or before "
										+ (new SimpleDateFormat("MMMM dd, yyyy").format((Calendar.getInstance()).getTime()))
										+ " are valid.";
							}
							else if ((item.getItemKMS() == 0) && (item.getItemMeals() == 0) && (item.getItemLodging() == 0)
									&& (item.getItemOther() == 0)) {
								iserror = true;
								errormessage = "All claim item values are zero (0).<br> Please enter one or more values greater then zero (0).";
							}
							else {
								TravelClaimItem item_old = TravelClaimItemsDB.getClaimItem(itemid);
								check = TravelClaimItemsDB.editClaimItem(claim, item_old, item);
								if (check) {
									request.setAttribute("RESULT", "SUCCESS");
									request.setAttribute("msg", "Claim item updated successfully.");
								}
								else {
									iserror = true;
									errormessage = "Claim item could not be updated.";
								}
							}
						}
						else {
							iserror = true;
							errormessage = "Claim item could not be updated.";
						}
					}
					else {
						iserror = true;
						errormessage = "Invalid operation.";
					}
				}
			}
		}
		if (iserror) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}
}