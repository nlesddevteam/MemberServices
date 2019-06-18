package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.PersonnelDB;
import com.awsd.ppgp.PPGPException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewGrowthPlanPrincipalSummaryRequestHandler extends RequestHandlerImpl {

	public ViewGrowthPlanPrincipalSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
				"PPGP-VIEW-SUMMARY", "PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (usr.checkPermission("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST")) {

			this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("pid", "Personnel PID needed.")
			});

			if (validate_form())
				request.setAttribute("PRINCIPAL", PersonnelDB.getPersonnel(form.getInt("pid")));
			else
				throw new PPGPException(validator.getErrorString());
		}
		if(request.getParameter("syear") != null){
			request.setAttribute("SYEAR", request.getParameter("syear"));
		}

		path = "principalSummary.jsp";

		return path;
	}
}