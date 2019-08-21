package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class MarkJobShortlistCompleteRequestHandler extends RequestHandlerImpl {

	public MarkJobShortlistCompleteRequestHandler() {

		requiredRoles = new String[] {
				"ADMINISTRATOR", "SEO - PERSONNEL", "SENIOR EDUCATION OFFICIER", "AD HR", "ASSISTANT DIRECTORS"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num"), new RequiredFormElement("closed")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				JobOpportunityBean opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if (opp != null) {
					if (form.getBoolean("closed")) {
						opp.setShortlistCompleteDate(new Date());
					}
					else {
						opp.setShortlistCompleteDate(null);
					}

					JobOpportunityManager.updateJobOpportunityBean(opp);

					session.setAttribute("JOB", opp);
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
					session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
							ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
					request.setAttribute("AD_REQUEST", AdRequestManager.getAdRequestBean(form.get("comp_num")));

					path = "admin_view_job_applicants_shortlist.jsp";
				}
				else {
					request.setAttribute("msg", "Invalid reques.");

					path = "admin_index.jsp";
				}
			}
			else {
				request.setAttribute("msg", "Cometition number required to view applicant shortlist.");

				path = "admin_view_job_posts.jsp";
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_job_posts.jsp";
		}

		return path;
	}
}