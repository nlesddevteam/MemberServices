package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeclineInterviewShortlistApplicantRequestHandler extends RequestHandlerImpl {

	public DeclineInterviewShortlistApplicantRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW","PERSONNEL-PRINCIPAL-VIEW","PERSONNEL-VICEPRINCIPAL-VIEW","PERSONNEL-OTHER-MANAGER-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("sin", "SIN required for operation.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		JobOpportunityBean opp = null;
		SubListBean list = null;
		try {

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
			else if (validate_form()) {
				if (opp != null) {
					if(request.getParameter("withd").equals("D")) {
						ApplicantProfileManager.declineInterviewShortlistApplicant(request.getParameter("sin"), opp);
					}else {
						ApplicantProfileManager.withdrawShortlistApplicant(request.getParameter("sin"), opp);
					}
					
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
					session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
							ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
					session.setAttribute("JOB_SHORTLIST_WITHDRAWS_MAP",
							ApplicantProfileManager.getApplicantShortlistInterviewWithdrawsMap(opp));
					if(opp.getIsSupport().equals("Y")) {
						request.setAttribute("AD_REQUEST", RequestToHireManager.getRequestToHireByCompNum(opp.getCompetitionNumber()));
					}else {
						request.setAttribute("AD_REQUEST", AdRequestManager.getAdRequestBean(opp.getCompetitionNumber()));
					}
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
				if(request.getParameter("withd").equals("D")) {
					request.setAttribute("msg", "Applicant flagged as DECLINED INTERVIEW.");
				}else {
					request.setAttribute("msg", "Applicant flagged as WITHDREW.");
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());

				path = "admin_index.jsp";
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
