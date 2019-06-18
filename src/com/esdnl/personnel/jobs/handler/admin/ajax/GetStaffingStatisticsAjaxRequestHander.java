package com.esdnl.personnel.jobs.handler.admin.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.StaffingStatisticsBean;
import com.esdnl.personnel.jobs.dao.StaffingStatisticsManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class GetStaffingStatisticsAjaxRequestHander extends RequestHandlerImpl {

	public GetStaffingStatisticsAjaxRequestHander() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW-STAFFING-STATS"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("sd")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				StaffingStatisticsBean stats = StaffingStatisticsManager.getStaffingStatisticsBean(form.getDate("sd"));

				// generate JSON for candidate details.
				PrintWriter out = response.getWriter();

				response.setContentType("application/json");
				response.setHeader("Cache-control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "-1");

				out.write(stats.toJSON());

				out.flush();
				out.close();
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
