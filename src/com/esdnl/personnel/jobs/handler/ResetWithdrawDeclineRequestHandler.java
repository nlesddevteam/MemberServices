package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ResetWithdrawDeclineRequestHandler extends RequestHandlerImpl {
	public ResetWithdrawDeclineRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num", "COMP_NUM required for cancellation"),
				new RequiredFormElement("sin", "SIN required for cancellation")
		});
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-RESET-WITH-DECLINE"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String path;
		super.handleRequest(request, response);
		if (validate_form()) {
			try
			{
				String comp_num = request.getParameter("comp_num");
				JobOpportunityManager.resetWithdrawDecline(comp_num, form.get("sin"));
				JobOpportunityBean opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));
				
				session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
				session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
						ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
				session.setAttribute("JOB_SHORTLIST_WITHDRAWS_MAP",
						ApplicantProfileManager.getApplicantShortlistInterviewWithdrawsMap(opp));
				session.setAttribute("JOB", opp);
				
				if (opp.getIsSupport().contentEquals("Y")) {
					RequestToHireBean rth = RequestToHireManager.getRequestToHireByCompNum(form.get("comp_num"));
					request.setAttribute("AD_REQUEST", rth);
				}
				else {
					AdRequestBean ad = AdRequestManager.getAdRequestBean(form.get("comp_num"));
					request.setAttribute("AD_REQUEST", ad);
				}
				
				path = "admin_view_job_applicants_shortlist.jsp";
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not reset withdraw/decline status.");
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}


		return path;
	}
}
