package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class ViewPoolHighlyRecommendedCandidatesListRequestHandler extends RequestHandlerImpl {

	public ViewPoolHighlyRecommendedCandidatesListRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
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

				opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));
				if ((opp != null) && opp.getJobType().equal(JobTypeConstant.POOL)) {
					session.setAttribute("JOB", opp);
					session.setAttribute("JOB_APPLICANTS",
							ApplicantProfileManager.getApplicantProfileBeanByJob(form.get("comp_num")));
					request.setAttribute("HIGHLY_RECOMMENDED_MAP",
							ApplicantProfileManager.getPoolCompetitionHighlyRecommendedCandidateMap(opp.getCompetitionNumber()));
					request.setAttribute("AD_REQUEST", AdRequestManager.getAdRequestBean(form.get("comp_num")));

					path = "admin_view_job_pool_highly_recommended_list.jsp";
				}
				else {
					request.setAttribute("msg", "Job competition " + form.get("comp_num") + " not found.");
					path = "admin_view_job_posts.jspp";
				}
			}
			else {
				request.setAttribute("msg", "Cometition number required to view pool highly recommended candidate list.");

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