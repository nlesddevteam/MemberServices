package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.EthicsDeclarationSummaryReportBean;
import com.esdnl.personnel.jobs.dao.EthicsDeclarationSummaryReportManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewEthicsSummaryRequestHandler extends RequestHandlerImpl {

	public ViewEthicsSummaryRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-ETHICS-DEC"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			EthicsDeclarationSummaryReportBean cbean = EthicsDeclarationSummaryReportManager.getSummaryReport();
			request.setAttribute("settings", cbean);
			path = "view_ethics_summary.jsp";
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "view_ethics_summary.jsp";
		}

		return path;
	}
}