package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.bean.TravelBudgetFiscalYearSummary;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteTravelBudgetRequestHandler extends RequestHandlerImpl {

	public DeleteTravelBudgetRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN", "TRAVEL-EXPENSE-DELETE-BUDGET"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("budget_id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			TravelBudgetService.deleteTravelBudget(form.getInt("budget_id"));
		}

		Collection<TravelBudget> budgets = TravelBudgetService.getTravelBudgets(Utils.getCurrentSchoolYear());

		double summary[] = new double[3];

		for (TravelBudget budget : budgets) {
			summary[0] += budget.getAmount();
			summary[1] += budget.getAmountClaimed();
			summary[2] += budget.getDeficit();
		}

		request.setAttribute("BUDGETS", budgets);
		request.setAttribute("SUMMARY", new TravelBudgetFiscalYearSummary(summary[0], summary[1], summary[2]));

		return "travel_budgets.jsp";
	}
}