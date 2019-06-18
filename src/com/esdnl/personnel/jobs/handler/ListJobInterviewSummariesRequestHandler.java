package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListJobInterviewSummariesRequestHandler extends RequestHandlerImpl {

	public ListJobInterviewSummariesRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id", "Applicant ID is required."),
				new RequiredFormElement("comp_num", "Job competition number is required.")
		});

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("id"));
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if (profile != null && job != null) {
					request.setAttribute("profile", profile);
					request.setAttribute("job", job);
					request.setAttribute("summaries", InterviewSummaryManager.getInterviewSummaryBeans(profile));

					path = "admin_list_job_interview_summaries.jsp";
				}
				else {
					request.setAttribute("msg",
							"Applicant [id=" + form.get("id") + "] and/or Competition[" + form.get("comp_num") + "] cannot be found.");

					path = "admin_index.jsp";
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());

				path = "admin_index.jsp";
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "admin_index.jsp";
		}

		return path;
	}

}
