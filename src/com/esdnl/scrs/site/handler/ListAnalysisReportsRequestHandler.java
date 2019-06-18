package com.esdnl.scrs.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;

public class ListAnalysisReportsRequestHandler extends RequestHandlerImpl {

	public ListAnalysisReportsRequestHandler() {

		this.requiredPermissions = new String[] {
			"BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		return "list_analysis_reports.jsp";
	}

}
