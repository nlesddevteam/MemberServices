package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;

public class AddExternalReferenceCheckRequestHandler extends PublicAccessRequestHandlerImpl {

	public AddExternalReferenceCheckRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			JobOpportunityBean job = null;
			ApplicantProfileBean profile = null;
			ReferenceCheckRequestBean refreq = null;

			refreq = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("id"));
			profile = ApplicantProfileManager.getApplicantProfileBean(refreq.getCandidateId());
			job = JobOpportunityManager.getJobOpportunityBean(refreq.getCompetitionNumber());

			request.setAttribute("REFERENCE_CHECK_REQUEST_BEAN", refreq);

			AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
			request.setAttribute("AD_REQUEST_BEAN", ad);

			request.setAttribute("JOB", job);

			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
			request.setAttribute("JOB_ASSIGNMENTS", ass);

			request.setAttribute("PROFILE", profile);

			path = "teacher_reference_checklist.jsp";

		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}

		return path;
	}
}
