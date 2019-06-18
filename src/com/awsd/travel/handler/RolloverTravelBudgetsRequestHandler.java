package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class RolloverTravelBudgetsRequestHandler extends RequestHandlerImpl {

	public RolloverTravelBudgetsRequestHandler() {

		this.requiredPermissions = new String[] {
			"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String sy = Utils.getCurrentSchoolYear();
		if (form.exists("sy"))
			sy = form.get("sy");

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Utils.getYear(Calendar.JUNE, sy) + 1, Calendar.JUNE, 1);

		String fsy = Utils.getSchoolYear(cal);

		TravelBudgetService.rolloverTravelBudgets(form.getInt("id"), sy, fsy);

		request.getRequestDispatcher("listTravelBudgets.html?school_year=" + fsy).forward(request, response);

		return null;
	}
}