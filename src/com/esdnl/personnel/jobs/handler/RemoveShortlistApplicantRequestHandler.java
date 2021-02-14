package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;

public class RemoveShortlistApplicantRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		HttpSession session = null;
		User usr = null;

		JobOpportunityBean opp = null;
		SubListBean list = null;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")) && !(usr.getUserPermissions().containsKey("PERSONNEL-OTHER-MANAGER-VIEW"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}
			}
			else {
				throw new SecurityException("User login required.");
			}

			if (session.getAttribute("JOB") != null) {
				opp = (JobOpportunityBean) session.getAttribute("JOB");
				path = "admin_view_job_applicants_shortlist.jsp";
			}
			else if (session.getAttribute("SUBLIST") != null) {
				list = (SubListBean) session.getAttribute("SUBLIST");
				path = "admin_view_sublist_applicants.jsp";
			}

			if ((opp == null) && (list == null)) {
				request.setAttribute("msg", "Choose a job/sublist.");
				path = "admin_index.jsp";
			}
			else if (request.getParameter("sin") == null) {
				request.setAttribute("msg", "SIN required for operation.");
			}
			else {
				if (opp != null) {
					ApplicantProfileManager.removeShortlistApplicant(request.getParameter("sin"), opp);
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
					session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
							ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
					session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(opp));
					AdRequestBean ad = AdRequestManager.getAdRequestBean(opp.getCompetitionNumber());
					if(opp.isSupport()) {
						request.setAttribute("AD_REQUEST", RequestToHireManager.getRequestToHireByCompNum(opp.getCompetitionNumber()));
					}else {
						request.setAttribute("AD_REQUEST", ad);
					}
					path = "admin_view_job_applicants_shortlist.jsp";
				}
				else if (list != null) {
					/*
					ApplicantProfileManager.shortListApplicant(request.getParameter("sin"), list);
					session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(list));
					session.setAttribute("SHORTLIST", ApplicantProfileManager.getApplicantShortlist(list));
					session.setAttribute("NOTAPPROVEDMAP", ApplicantProfileManager.getApplicantsNotApprovedMap(list));
					session.setAttribute("NOTAPPROVED", ApplicantProfileManager.getApplicantsNotApproved(list));
					*/
				}

				request.setAttribute("msg", "Applicant removed from shortlist successfully.");
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve applicants.");
			path = "admin_index.jsp";
		}

		return path;
	}

}
