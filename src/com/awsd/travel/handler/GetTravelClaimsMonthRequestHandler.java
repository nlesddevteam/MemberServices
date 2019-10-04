package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.security.User;
import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetTravelClaimsMonthRequestHandler extends RequestHandlerImpl {

	public GetTravelClaimsMonthRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String schoolyear = form.get("syear");
		User usr = (User) session.getAttribute("usr");
		TreeMap<Integer, TravelClaim> year_claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), schoolyear);
		try {
			Calendar cal = Calendar.getInstance();
			int cur_month;
			boolean isValues = false;
			if (Utils.getCurrentSchoolYear().equals(request.getAttribute("YEAR-SELECT"))) {
				cur_month = cal.get(Calendar.MONTH);
			}
			else {
				cur_month = Calendar.JUNE;
			}
			String selectedyear = schoolyear.substring(0, schoolyear.indexOf("-"));
			cal.set(Calendar.YEAR, Integer.parseInt(selectedyear));
			cal.set(Calendar.MONTH, Calendar.JULY);
			String cur_year = Utils.getSchoolYear(cal);
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<MONTHS>");
			while (cur_year.equalsIgnoreCase(Utils.getSchoolYear(cal)) && (cal.get(Calendar.MONTH) != cur_month)) {
				if ((year_claims == null) || (year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null)
						|| (year_claims.get(new Integer(cal.get(Calendar.MONTH))) instanceof PDTravelClaim)) {
					sb.append("<MONTH>");
					sb.append("<MONTHNUMBER>" + cal.get(Calendar.MONTH) + "</MONTHNUMBER>");
					sb.append("<MONTHNAME>" + Utils.getMonthString(cal.get(Calendar.MONTH)) + "</MONTHNAME>");
					sb.append("<SCHOOLYEAR>" + cal.get(Calendar.YEAR) + "</SCHOOLYEAR>");
					sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
					sb.append("</MONTH>");
					isValues = true;

				}
				cal.add(Calendar.MONTH, 1);
			}
			if ((year_claims == null) || (year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null)) {
				sb.append("<MONTH>");
				sb.append("<MONTHNUMBER>" + cur_month + "</MONTHNUMBER>");
				sb.append("<MONTHNAME>" + Utils.getMonthString(cur_month) + "</MONTHNAME>");
				sb.append("<SCHOOLYEAR>" + cal.get(Calendar.YEAR) + "</SCHOOLYEAR>");
				sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
				sb.append("</MONTH>");
				isValues = true;
			}
			if (!isValues) {
				sb.append("<MONTH>");
				sb.append("<MESSAGE>NOLIST</MESSAGE>");
				sb.append("</MONTH>");
			}
			sb.append("</MONTHS>");
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