package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class EditGrowthPlanGoalRequestHandler extends RequestHandlerImpl {

	public EditGrowthPlanGoalRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("gid")
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
				request.setAttribute("PPGP_GOAL", PPGPDB.getPPGPGoal(form.getInt("gid")));
			else
				throw new PPGPException("Goal ID required for deletion.");

			path = "editGoal.jsp";
		}

		return path;
	}
}