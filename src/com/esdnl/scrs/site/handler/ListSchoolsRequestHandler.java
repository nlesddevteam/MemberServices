package com.esdnl.scrs.site.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.SchoolBullyingIncidentCount;
import com.esdnl.scrs.service.SchoolBullyingIncidentCountService;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListSchoolsRequestHandler extends RequestHandlerImpl {

	public ListSchoolsRequestHandler() {

		this.requiredPermissions = new String[] {
			"BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				ArrayList<SchoolBullyingIncidentCount> counts = SchoolBullyingIncidentCountService.getSchoolBullyingIncidentCounts();

				int total = 0;
				int weekly = 0;
				int monthly = 0;

				for (SchoolBullyingIncidentCount count : counts) {
					total += count.getOverallIncidentCount();
					weekly += count.getWeeklyIncidentCount();
					monthly += count.getMonthlyIncidentCount();
				}

				request.setAttribute("counts", counts);
				request.setAttribute("overallTotal", total);
				request.setAttribute("weeklyTotal", weekly);
				request.setAttribute("monthlyTotal", monthly);

			}
			else {
				System.out.println(validator.getErrorString());
			}

			path = "list_schools.jsp";
		}
		catch (BullyingException e) {
			e.printStackTrace();

			request.setAttribute("msg", e.getMessage());

			path = "index.html";
		}

		return path;
	}

}
