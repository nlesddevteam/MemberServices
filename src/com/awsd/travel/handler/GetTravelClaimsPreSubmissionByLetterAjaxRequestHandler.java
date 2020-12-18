package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetTravelClaimsPreSubmissionByLetterAjaxRequestHandler extends RequestHandlerImpl {

	public GetTravelClaimsPreSubmissionByLetterAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String searchletter = request.getParameter("letter");
		LinkedHashMap<Integer, Vector<TravelClaim>> travelclaims = null;
		try {
			if (searchletter.equals("All")) {
				travelclaims = TravelClaimDB.getClaimsPreSubmissionTreeMap();
			}
			else {
				travelclaims = TravelClaimDB.getClaimsPreSubmissionLetterTreeMap(searchletter);
			}
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			if ((travelclaims != null) && (travelclaims.size() > 0)) {
				Iterator iter = travelclaims.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry item = (Map.Entry) iter.next();
					Personnel p = new Personnel(((Integer) item.getKey()).intValue());
					Iterator p_iter = ((Vector) item.getValue()).iterator();
					while (p_iter.hasNext()) {
						TravelClaim claim = (TravelClaim) p_iter.next();
						if (!(claim.getClass().getName().toString().equals("com.awsd.travel.PDTravelClaim"))) {
							sb.append("<CLAIM>");
							sb.append("<EMPLOYEE>" + p.getFullName() + "</EMPLOYEE>");
							sb.append("<TITLE>" + Utils.getMonthString(claim.getFiscalMonth()) + " "
									+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + "</TITLE>");
							sb.append("<TYPE>Monthly</TYPE>");
							sb.append("<ZONE>" + ((p.getSchool() !=null)?p.getSchool().getZone():"UNKNOWN") + "</ZONE>");				
							if (claim.getSupervisor() == null) {
								sb.append("<SUPERVISOR>N/A</SUPERVISOR>");
							}
							else {
								sb.append("<SUPERVISOR>" + claim.getSupervisor().getFullName() + "</SUPERVISOR>");
							}
							sb.append("<CLAIMDATE>" + Utils.getMonthString(claim.getFiscalMonth()) + ","
									+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + "</CLAIMDATE>");
							sb.append("<TOTAL>" + claim.getSummaryTotals().getSummaryTotal()+ "</TOTAL>");	
							sb.append("<ID>" + claim.getClaimID() + "</ID>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</CLAIM>");
						}
						else {
							sb.append("<CLAIM>");
							sb.append("<EMPLOYEE>" + p.getFullName() + "</EMPLOYEE>");
							sb.append("<TITLE>" + "PD - "
									+ ((PDTravelClaim) claim).getPD().getTitle().replaceAll("&", "&amp;").replaceAll("\"", "&quot;")
									+ "</TITLE>");
							sb.append("<ZONE>" + ((p.getSchool() !=null)?p.getSchool().getZone():"UNKNOWN") + "</ZONE>");				
							if (claim.getSupervisor() == null) {
								sb.append("<SUPERVISOR>N/A</SUPERVISOR>");
							}
							else {
								sb.append("<SUPERVISOR>" + claim.getSupervisor().getFullName() + "</SUPERVISOR>");
							}
							sb.append("<CLAIMDATE>" + Utils.getMonthString(claim.getFiscalMonth()) + ","
									+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + "</CLAIMDATE>");
							sb.append("<TYPE>PD</TYPE>");
							sb.append("<TOTAL>" + claim.getSummaryTotals().getSummaryTotal()+ "</TOTAL>");	
							sb.append("<ID>" + claim.getClaimID() + "</ID>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</CLAIM>");
						}
					}
				}
			}
			else {
				sb.append("<CLAIM>");
				sb.append("<MESSAGE>NOCLAIMSFOUND</MESSAGE>");
				sb.append("</CLAIM>");
			}
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			path = null;
		}
		return path;
	}
}
