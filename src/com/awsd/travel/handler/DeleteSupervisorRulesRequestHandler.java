package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteSupervisorRulesRequestHandler extends RequestHandlerImpl {

	public DeleteSupervisorRulesRequestHandler() {

		this.requiredPermissions = new String[] {
			"TRAVEL-CLAIM-ADMIN"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("rule_id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			TravelClaimSupervisorRuleDB.deleteSupervisorRule(form.getInt("rule_id"));
		}

		request.setAttribute("SUPERVISOR_RULES", TravelClaimSupervisorRuleDB.getTravelClaimSupervisorRules());

		return "supervisor_rules.jsp";
	}
}