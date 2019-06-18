package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteGrowthPlanTaskRequestHandler extends RequestHandlerImpl {

	public DeleteGrowthPlanTaskRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("tid")
		});

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Personnel teacher = null;
		Boolean policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null) && !(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY"))) {
			path = "policy.jsp";
		}
		else {
			if (validate_form()) {
				PPGPDB.deletePPGPGoalTask(form.getInt("tid"));

				if (form.exists("pid")) {
					teacher = PersonnelDB.getPersonnel(form.getInt("pid"));
					request.setAttribute("TEACHER", teacher);
				}

				request.setAttribute("PPGP", PPGPDB.getPPGP(form.getInt("ppgpid")));
			}
			else
				throw new PPGPException("Task ID required for deletion.");

			path = "teacherSummary.jsp";
		}

		return path;
	}
}