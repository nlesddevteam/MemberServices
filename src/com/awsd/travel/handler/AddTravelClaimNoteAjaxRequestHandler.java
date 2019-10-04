package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimNote;
import com.awsd.travel.TravelClaimNoteDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelClaimNoteAjaxRequestHandler extends RequestHandlerImpl {

	public AddTravelClaimNoteAjaxRequestHandler() {

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
		String errormessage = "";

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);
			if (claim != null) {
				if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
					if (TravelClaimNoteDB.addClaimNote(claim,
							new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")))) {
						errormessage = "SUCCESS";
					}
					else {
						errormessage = "Note could not be added.";
					}
				}
				else {
					errormessage = "Note cannot be empty.";
				}
			}
			else {
				errormessage = "claim_error.jsp?msg=Claim cound not be found.";
			}
		}
		else {
			errormessage = "claim_error.jsp?msg=Claim ID Required For ADD Note Operation.";
		}
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
		return null;
	}
}