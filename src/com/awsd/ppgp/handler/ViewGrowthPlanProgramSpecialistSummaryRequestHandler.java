package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;

public class ViewGrowthPlanProgramSpecialistSummaryRequestHandler extends RequestHandlerImpl {

	public ViewGrowthPlanProgramSpecialistSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		return "programSpecialistSummary.jsp";
	}
}