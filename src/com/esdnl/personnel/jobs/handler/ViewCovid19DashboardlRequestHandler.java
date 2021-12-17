package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.Covid19DashboardBean;
import com.esdnl.personnel.jobs.dao.Covid19DashboardManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewCovid19DashboardlRequestHandler extends RequestHandlerImpl {

	public ViewCovid19DashboardlRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-COVID19","PERSONNEL-ADMIN-VIEW-COVID19-STATUS"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			Covid19DashboardBean cbean = Covid19DashboardManager.getCovid19DashboardReport();
			request.setAttribute("settings", cbean);
			path = "view_covid19_dashboard.jsp";
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "view_covid19_dashboard.jsp";
		}

		return path;
	}
}