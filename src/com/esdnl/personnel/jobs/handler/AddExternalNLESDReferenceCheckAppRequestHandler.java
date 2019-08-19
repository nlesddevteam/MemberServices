package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddExternalNLESDReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl {

	public AddExternalNLESDReferenceCheckAppRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("reftype"), new RequiredFormElement("refreq")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				ApplicantRefRequestBean abean = ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("refreq"));
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(abean.getApplicantId());

				if ((abean != null) && (profile != null)) {
					String referenceType = form.get("reftype").toString();

					//now we load the correct page
					//TODO: ARE ALL OF THESE PUBLICALLY AVAILABLE!!!!
					if (referenceType.equals("admin")) {
						path = "nlesd_admin_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("guide")) {
						path = "nlesd_guide_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("teacher")) {
						path = "nlesd_teacher_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("external")) {
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
						return "/MemberServices/memberservices.html";
					}

					request.setAttribute("PROFILE", profile);
					request.setAttribute("REFREQUEST", abean);
				}
				else {
					path = "/MemberServices/memberservices.html";
				}
			}
			catch (Exception e) {
				System.err.println("[AddExternalNLESDReferenceCheckAppRequestHandler] " + e.getMessage());

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
