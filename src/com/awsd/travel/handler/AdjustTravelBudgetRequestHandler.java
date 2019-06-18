package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AdjustTravelBudgetRequestHandler extends RequestHandlerImpl {

	public AdjustTravelBudgetRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN", "TRAVEL-EXPENSE-EDIT-BUDGET"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("budget_id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (form.hasValue("op", "CONFIRM")) {

			this.validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("budget_id"), new RequiredFormElement("budget_adjustment")
			});

			if (validate_form()) {
				TravelBudget budget = TravelBudgetService.getTravelBudget(form.getInt("budget_id"));

				budget.setAmount(budget.getAmount() + form.getDouble("budget_adjustment"));

				TravelBudgetService.updateTravelBudget(budget);

				request.setAttribute("msg", "Travel budget for \"" + budget.getPersonnel().getFullName()
						+ "\"  updated successfully.");

				request.setAttribute("BUDGET", budget);
			}
			else
				request.setAttribute("msg", this.validator.getErrorString());

			path = "adjust_travel_budget.jsp";

		}
		else {
			this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("budget_id")
			});

			if (validate_form())
				request.setAttribute("BUDGET", TravelBudgetService.getTravelBudget(form.getInt("budget_id")));
			else
				request.setAttribute("msg", this.validator.getErrorString());

			path = "adjust_travel_budget.jsp";
		}

		return path;
	}

}
