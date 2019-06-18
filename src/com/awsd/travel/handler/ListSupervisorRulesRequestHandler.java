package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListSupervisorRulesRequestHandler extends RequestHandlerImpl {

	public ListSupervisorRulesRequestHandler() {

		this.requiredPermissions = new String[] {
			"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		request.setAttribute("SUPERVISOR_RULES", TravelClaimSupervisorRuleDB.getTravelClaimSupervisorRules());

		return "supervisor_rules.jsp";
	}
}