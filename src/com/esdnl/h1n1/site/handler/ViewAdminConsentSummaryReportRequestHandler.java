package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.dao.SchoolStatsManager;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewAdminConsentSummaryReportRequestHandler extends RequestHandlerImpl {

	public ViewAdminConsentSummaryReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_consent_data.jsp";

		try {
			request.setAttribute("SCHOOLCONSENTDATASUMMARY", DailyReportManager.getSchoolConsentDataSummary());
			request.setAttribute("CONSENTDATASUMMARY", DailyReportManager.getConsentDataSummary());
			request.setAttribute("SCHOOLSTATSSUMMARYBEAN", SchoolStatsManager.getSchoolStatsSummaryBean());
			request.setAttribute("TODAY", Calendar.getInstance().getTime());
		}
		catch (H1N1Exception e) {
			e.printStackTrace();
		}

		return path;

	}

}
