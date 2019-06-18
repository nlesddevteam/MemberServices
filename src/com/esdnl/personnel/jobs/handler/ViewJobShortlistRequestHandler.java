package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewJobShortlistRequestHandler extends RequestHandlerImpl {

	public ViewJobShortlistRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("comp_num")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				JobOpportunityBean opp = null;
				AdRequestBean ad = null;

				opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));
				if (!opp.isCandidateListPrivate() || usr.checkPermission("PERSONNEL-ADMIN-VIEW-PRIVATE-CANDIDATE-LIST")) {

					if (opp.getJobType().equal(JobTypeConstant.LEADERSHIP) && usr.checkRole("SENIOR EDUCATION OFFICIER")
							&& !usr.checkRole("JOB APPS - VIEW PRIVATE")) {
						request.setAttribute("msg", "Applicant List for competition  " + form.get("comp_num") + " is private.");
						path = "admin_index.jsp";
					}
					else {
						session.setAttribute("JOB", opp);

						if (!form.exists("alphabetized")
								&& (opp.getJobType().equal(JobTypeConstant.POOL) || opp.getJobType().equal(
										JobTypeConstant.SUMMER_SCHOOL))) {
							session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getPoolShortlistMap(opp));
							path = "admin_view_job_pool_applicants_shortlist.jsp";
						}
						else {
							session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
							session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
									ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
							path = "admin_view_job_applicants_shortlist.jsp";
						}

						ad = AdRequestManager.getAdRequestBean(form.get("comp_num"));
						request.setAttribute("AD_REQUEST", ad);

					}
				}
				else {
					request.setAttribute("msg", "Applicant List for competition  " + form.get("comp_num") + " is private.");
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