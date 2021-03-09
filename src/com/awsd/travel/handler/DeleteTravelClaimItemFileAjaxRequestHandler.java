package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.dao.TravelClaimFileManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteTravelClaimItemFileAjaxRequestHandler extends RequestHandlerImpl {

	public DeleteTravelClaimItemFileAjaxRequestHandler() {

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
		int id = -1;
		boolean check = false;
		int cid = -1;
		String fileName="";

		try {
			id = form.getInt("id");
			cid = form.getInt("clid");
			fileName = form.get("filename");
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id < 1) {
			//send error back
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<STATUS>ERROR</STATUS>");
			sb.append("<MESSAGE>CLAIM ITEM ID IS REQUIRED FOR DELETE FILE OPERATION</MESSAGE>");
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
			throw new TravelClaimException("<<<<< CLAIM ITEM ID IS REQUIRED FOR DELETE FILE OPERATION.  >>>>>");
		}
		else {
			claim = TravelClaimDB.getClaim(cid);
			String msg = "";
			String status = "";
			if ((claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID())) {
				msg = "You do not have permission to delete this travel claim item.";
				status = "ERROR";
			}
			else {
				op = request.getParameter("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						check = TravelClaimFileManager.deleteTravelClaimFile(fileName,id);
						delete_file("/Travel/Attachments/", fileName);
						if (check) {
							msg = "Travel Claim Item File Deleted";
							status = "SUCCESS";
						}
						else {
							msg = "Travel Claim Item File Not Deleted";
							status = "ERROR";
						}
					}
					else {
						msg = "Invalid operation";
						status = "ERROR";
					}
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
		}
		return null;
	}
}