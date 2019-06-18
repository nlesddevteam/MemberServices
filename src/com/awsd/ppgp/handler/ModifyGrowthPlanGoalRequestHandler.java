package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.PPGPGoal;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ModifyGrowthPlanGoalRequestHandler extends RequestHandlerImpl {

	public ModifyGrowthPlanGoalRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("gid", "Goal ID required for PPGP."),
				new RequiredFormElement("goal", "Goal required for PPGP.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Boolean policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null) && !usr.checkPermission("PPGP-VIEW-SUMMARY"))
			path = "policy.jsp";
		else {
			if (validate_form())
				PPGPDB.modifyPPGPGoal(new PPGPGoal(form.getInt("gid"), form.get("goal")));
			else
				throw new PPGPException(validator.getErrorString());

			path = "teacherSummary.jsp";
		}

		return path;
	}
}