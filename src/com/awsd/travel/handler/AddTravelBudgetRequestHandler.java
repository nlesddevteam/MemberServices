package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.TravelClaimSupervisorRule;
import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.constant.KeyType;
import com.awsd.travel.service.DivisionService;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddTravelBudgetRequestHandler extends RequestHandlerImpl {

	public AddTravelBudgetRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN", "TRAVEL-EXPENSE-EDIT-BUDGET"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (form.exists("op")) {
			if (form.hasValue("op", "PERSONNEL_FILTER")) // AJAX CALL
			{
				Personnel prec[] = null;

				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<PERSONNEL-LIST>");

				prec = PersonnelDB.searchPersonnel(form.get("criteria"));

				for (Personnel p : prec)
					sb.append(p.toXML());

				sb.append("</PERSONNEL-LIST>");
				xml = StringUtils.encodeXML(sb.toString());

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;
			}
			else if (form.hasValue("op", "CONFIRM")) {

				this.validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("personnel_id"), new RequiredFormElement("fiscal_year"),
						new RequiredFormElement("budgeted_amount")
				});

				if (validate_form()) {
					TravelBudget budget = new TravelBudget();

					if (form.exists("budget_id"))
						budget.setBudgetId(form.getInt("budget_id"));
					budget.setPersonnel(PersonnelDB.getPersonnel(form.getInt("personnel_id")));
					budget.setFiscalYear(form.get("fiscal_year"));
					budget.setAmount(form.getDouble("budgeted_amount"));

					if (budget.getBudgetId() > 0) {
						TravelBudgetService.updateTravelBudget(budget);

						request.setAttribute("msg", "Travel budget for \"" + budget.getPersonnel().getFullName()
								+ "\"  updated successfully.");
					}
					else {
						TravelBudgetService.addTravelBudget(budget);
						request.setAttribute("msg", "Travel budget for \"" + budget.getPersonnel().getFullName()
								+ "\"  added successfully.");
					}

					if (form.exists("supervisor_id") && form.exists("division_id")) {
						TravelClaimSupervisorRule rule = new TravelClaimSupervisorRule();
						rule.setDivision(DivisionService.getDivision(form.getInt("division_id")));
						rule.setEmployeeKey(form.get("personnel_id"));
						rule.setEmployeeKeyType(KeyType.USER);
						rule.setSupervisorKey(form.get("supervisor_id"));
						rule.setSupervisorKeyType(KeyType.USER);

						TravelClaimSupervisorRuleDB.addSupervisorRule(rule);
					}

					path = "add_travel_budget.jsp";
				}
				else {
					request.setAttribute("msg", this.validator.getErrorString());
					path = "add_travel_budget.jsp";
				}
			}
		}
		else {
			if (form.exists("budget_id"))
				request.setAttribute("BUDGET", TravelBudgetService.getTravelBudget(form.getInt("budget_id")));

			path = "add_travel_budget.jsp";
		}

		request.setAttribute("FISCALYEARS", getFiscalYearList());
		request.setAttribute("DIVISIONS", DivisionService.getDivisions());

		return path;
	}

	private ArrayList<String> getFiscalYearList() {

		ArrayList<String> fy = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);

		for (int i = 1; i < 5; i++) {
			fy.add(Utils.getSchoolYear(cal));
			cal.add(Calendar.YEAR, -1);
		}

		return fy;
	}
}
