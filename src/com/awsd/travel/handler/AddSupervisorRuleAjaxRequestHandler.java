package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaimSupervisorRule;
import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.awsd.travel.constant.KeyType;
import com.awsd.travel.service.DivisionService;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddSupervisorRuleAjaxRequestHandler extends RequestHandlerImpl {

	public AddSupervisorRuleAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String errormessage = "";
		try {
			TravelClaimSupervisorRule rule = null;

			if (Integer.parseInt(request.getParameter("rule_id")) > 0) {
				rule = TravelClaimSupervisorRuleDB.getTravelClaimSupervisorRule(
						Integer.parseInt(request.getParameter("rule_id")));
			}
			else {
				rule = new TravelClaimSupervisorRule();
			}
			rule.setSupervisorKeyType(KeyType.get(Integer.parseInt(request.getParameter("supervisor_keytype"))));
			rule.setSupervisorKey(request.getParameter("supervisor_key"));
			rule.setEmployeeKeyType(KeyType.get(Integer.parseInt(request.getParameter("user_keytype"))));
			rule.setEmployeeKey(request.getParameter("user_key"));
			rule.setDivision(DivisionService.getDivision(Integer.parseInt(request.getParameter("division_id"))));
			if (rule.getRuleId() <= 0) {
				TravelClaimSupervisorRuleDB.addSupervisorRule(rule);
				errormessage = "ADDED";
			}
			else {
				TravelClaimSupervisorRuleDB.updateSupervisorRule(rule);
				errormessage = "UPDATED";
			}
		}
		catch (Exception e) {
			System.err.println(e);
			errormessage = e.getMessage();
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