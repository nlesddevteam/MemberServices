package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ChangeSupervisorAjaxRequestHandler extends RequestHandlerImpl {

	public ChangeSupervisorAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		String op = "";
		boolean check = false;
		Personnel supervisor = null;

		claim = TravelClaimDB.getClaim(Integer.parseInt(request.getParameter("claim_id")));
		op = request.getParameter("op");
		String msg = "";
		String status = "";
		if (op != null) {
			if (op.equalsIgnoreCase("CONFIRM")) {
				try {
					supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
				}
				catch (NumberFormatException e) {
					supervisor = null;
					msg = "Error Setting Supervisor";
					status = "ERROR";
				}
				//all clear
				check = TravelClaimDB.setSupervisor(claim, supervisor);
				if (check) {
					msg = "Travel Claim Supervisor Updated";
					status = "SUCCESS";
				}
				else {
					supervisor = null;
					msg = "Error Setting Supervisor";
					status = "ERROR";
				}
			}
			else {
				supervisor = null;
				msg = "Invlaid Option";
				status = "ERROR";
			}
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<TRAVELCLAIMS>");
		sb.append("<TRAVELCLAIM>");
		sb.append("<STATUS>" + status + "</STATUS>");
		sb.append("<MESSAGE>" + msg + "</MESSAGE>");
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