package com.awsd.travel.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.bean.TravelBudgetFiscalYearSummary;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListTravelBudgetsRequestHandler extends RequestHandlerImpl {

	public ListTravelBudgetsRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN", "TRAVEL-CLAIM-SUPERVISOR-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Collection<TravelBudget> budgets = null;

		String sy = Utils.getCurrentSchoolYear();
		if (form.exists("school_year"))
			sy = form.get("school_year");

		if (usr.checkRole("ADMINISTRATOR"))
			budgets = TravelBudgetService.getTravelBudgets(sy);
		else if (usr.checkRole("ASSISTANT DIRECTORS"))
			budgets = TravelBudgetService.getTravelBudgetsByAssistantDirector(usr.getPersonnel(), sy);
		else
			budgets = TravelBudgetService.getTravelBudgetsBySupervisor(usr.getPersonnel(), sy);

		double summary[] = new double[3];

		for (TravelBudget budget : budgets) {
			summary[0] += budget.getAmount();
			summary[1] += budget.getAmountClaimed();
			summary[2] += budget.getDeficit();
		}

		request.setAttribute("FISCALYEARS", getFiscalYearList());
		request.setAttribute("BUDGETS", budgets);
		request.setAttribute("SUMMARY", new TravelBudgetFiscalYearSummary(summary[0], summary[1], summary[2]));
		request.setAttribute("sy", sy);

		return "travel_budgets.jsp";
	}

	private ArrayList<String> getFiscalYearList() {

		ArrayList<String> fy = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);

		for (int i = 1; i < 7; i++) {
			fy.add(Utils.getSchoolYear(cal));
			cal.add(Calendar.YEAR, -1);
		}

		return fy;
	}
}