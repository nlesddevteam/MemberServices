package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddExternalNLESDReferenceCheckRequestHandler extends PublicAccessRequestHandlerImpl {

	public AddExternalNLESDReferenceCheckRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id"), new RequiredFormElement("reftype")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				ReferenceCheckRequestBean refreq = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("id"));
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(refreq.getCompetitionNumber());

				if ((refreq != null) && (job != null)) {
					String referenceType = form.get("reftype");

					//now we load the correct page
					//TODO: ARE ALL THESE PAGES PUBLICALLY ACCESSIBLE?????
					if (referenceType.equals("admin")) {
						path = "nlesd_admin_reference_checklist.jsp";
					}
					else if (referenceType.equals("guide")) {
						path = "nlesd_guide_reference_checklist.jsp";
					}
					else if (referenceType.equals("teacher")) {
						path = "nlesd_teacher_reference_checklist.jsp";
					}
					else if (referenceType.equals("external")) {
						path = "nlesd_external_reference_checklist.jsp";
					}
					else if (referenceType.equals("manage")) {
						path = "nlesd_manage_reference_checklist.jsp";
					}
					else if (referenceType.equals("support")) {
						path = "nlesd_support_reference_checklist.jsp";
					}
					else {
						//TODO: WILL BE EVER GET HERE???
						return "/MemberServices/memberservices.html";
					}

					request.setAttribute("REFERENCE_CHECK_REQUEST_BEAN", refreq);
					request.setAttribute("JOB", job);
					request.setAttribute("AD_REQUEST_BEAN", AdRequestManager.getAdRequestBean(job.getCompetitionNumber()));
					request.setAttribute("JOB_ASSIGNMENTS",
							JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job));
					request.setAttribute("PROFILE", ApplicantProfileManager.getApplicantProfileBean(refreq.getCandidateId()));
				}
				else {
					path = "/MemberServices/memberservices.html";
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());

				path = "/MemberServices/memberservices.html";
			}
		}
		else {
			path = "/MemberServices/memberservices.html";
		}

		return path;
	}
}
