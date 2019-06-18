package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class SurrenderTravelBudgetsRequestHandler extends RequestHandlerImpl {

	public SurrenderTravelBudgetsRequestHandler() {

		this.requiredPermissions = new String[] {
			"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String sy = form.get("sy");
		int id = form.getInt("id");

		TravelBudgetService.surrenderTravelBudgets(id, sy);

		request.getRequestDispatcher("listTravelBudgets.html?school_year=" + sy).forward(request, response);

		return null;
	}
}