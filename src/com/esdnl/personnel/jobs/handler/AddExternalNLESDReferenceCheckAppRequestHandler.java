package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddExternalNLESDReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl {

	public AddExternalNLESDReferenceCheckAppRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("reftype")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		ApplicantRefRequestBean abean = null;
		ReferenceCheckRequestBean rbean = null;
		ApplicantProfileBean profile= null;
		if (validate_form()) {
			try {
				if(form.exists("refreq")) {
					abean = ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("refreq"));
					//check to see if this is null, applicant might have deleted requests
					//show nice message to user instead of nothing
					if(abean == null) {
						path = "view_nlesd_reference_error_app.jsp";
						request.setAttribute("msg", "Error finding reference request. Requester might have deleted the request.  Please check with the requester");
						return path;
					}else {
						profile = ApplicantProfileManager.getApplicantProfileBean(abean.getApplicantId());
					}
					
				}else if(form.exists("id")) {
					rbean = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("id"));
					profile = ApplicantProfileManager.getApplicantProfileBean(rbean.getCandidateId());
				}
				
				

				if ((abean != null || rbean != null) && (profile != null)) {
					String referenceType = form.get("reftype").toString();

					//now we load the correct page
					//TODO: ARE ALL OF THESE PUBLICALLY AVAILABLE!!!!
					//admin, guide, and teacher only available to people logged into MS and correct rights
					//teacher guide and admin to be removed in future after all old links have been completed
					if (referenceType.equals("admin")) {
						request.setAttribute("hidesearch",true);
						path = "add_nlesd_admin_reference.jsp";
					}
					else if (referenceType.equals("guide")) {
						request.setAttribute("hidesearch",true);
						path = "add_nlesd_guide_reference.jsp";
					}
					else if (referenceType.equals("teacher")) {
						request.setAttribute("hidesearch",true);
						path = "add_nlesd_teacher_reference.jsp";
					}else if (referenceType.equals("external")) {
						path = "nlesd_external_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("support")) {
						path = "nlesd_support_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("manage")) {
						path = "nlesd_manage_reference_checklist_app.jsp";
					}
					else {
						//TODO: SHOULD WE EVER GET HERE....YES QUERY PARAM COULD BE MANUALLY CHANGED!!!!!
						request.setAttribute("msg","Error retrieving reference request");
						return "view_nlesd_reference_error_app.jsp";
					}

					request.setAttribute("PROFILE", profile);
					if(abean != null) {
						request.setAttribute("arefreq", abean);
					}
					if(rbean != null) {
						request.setAttribute("refreq", rbean);
					}
				}
				else {
					request.setAttribute("msg","Error retrieving reference request");
					path = "view_nlesd_reference_error_app.jsp";
				}
			}
			catch (Exception e) {
				System.err.println("[AddExternalNLESDReferenceCheckAppRequestHandler] " + e.getMessage());

				request.setAttribute("msg", e.getMessage());

				path = "view_nlesd_reference_error_app.jsp";
			}
		}
		else {
			request.setAttribute("msg","Error retrieving reference request");
			path = "view_nlesd_reference_error_app.jsp";
		}

		return path;
	}
}
