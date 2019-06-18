package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ReferenceManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class ViewPrincipalCompletedReferenceChecksRequestHandler extends RequestHandlerImpl {

	public ViewPrincipalCompletedReferenceChecksRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW",
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				request.setAttribute("refs", ReferenceManager.getReferenceBeans(usr.getPersonnel()));

				path = "admin_view_principal_completed_reference_checks.jsp";
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not retrieve completed references.");

				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

			path = "admin_index.jsp";
		}

		return path;
	}

}
