package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.TravelClaimSupervisorRule;
import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.constant.KeyType;
import com.awsd.travel.service.DivisionService;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelBudgetAjaxRequestHandler extends RequestHandlerImpl {

	public AddTravelBudgetAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String errormessage = "";
		session = request.getSession(false);
		TravelBudget budget = new TravelBudget();
		try {
			budget.setBudgetId(Integer.parseInt(request.getParameter("budget_id")));
			budget.setPersonnel(PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("personnel_id"))));
			budget.setFiscalYear(request.getParameter("fiscal_year"));
			budget.setAmount(Double.parseDouble(request.getParameter("budgeted_amount")));
			if (budget.getBudgetId() > 0) {
				TravelBudgetService.updateTravelBudget(budget);
				errormessage = "UPDATED";
			}
			else {
				TravelBudgetService.addTravelBudget(budget);
				errormessage = "ADDED";
			}
			if (Integer.parseInt(request.getParameter("supervisor_id")) > 0
					&& Integer.parseInt(request.getParameter("division_id")) > 0) {
				TravelClaimSupervisorRule rule = new TravelClaimSupervisorRule();
				rule.setDivision(DivisionService.getDivision(Integer.parseInt(request.getParameter("division_id"))));
				rule.setEmployeeKey(request.getParameter("personnel_id"));
				rule.setEmployeeKeyType(KeyType.USER);
				rule.setSupervisorKey(request.getParameter("supervisor_id"));
				rule.setSupervisorKeyType(KeyType.USER);

				TravelClaimSupervisorRuleDB.addSupervisorRule(rule);
			}
		}
		catch (Exception e) {
			errormessage = "Travel Claim Error:" + e.getMessage();
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