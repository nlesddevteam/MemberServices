package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.ApplicantJobAppliedManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewApplicantProfileRequestHandler extends RequestHandlerImpl {

	public ViewApplicantProfileRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sin")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				ApplicantProfileBean abean = ApplicantProfileManager.getApplicantProfileBean(form.get("sin"));

				if (abean != null) {
					if (abean.getProfileType().equals("S")) {
						path = "admin_view_applicant_ss.jsp";
					}
					else {
						request.setAttribute("SUBLISTBEANS_BY_REGION", SubListManager.getSubListBeansByRegion(
								com.awsd.common.Utils.getCurrentSchoolYear(), SubstituteListConstant.TEACHER));
						path = "admin_view_applicant.jsp";
					}

					request.setAttribute("APPLICANT", abean);
					request.setAttribute("jobs", ApplicantJobAppliedManager.getApplicantJobsApplied(form.get("sin")));
				}
				else {
					request.setAttribute("msg", "Invalid request.");

					path = "admin_index.jsp";
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not retrieve applicant record.");

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