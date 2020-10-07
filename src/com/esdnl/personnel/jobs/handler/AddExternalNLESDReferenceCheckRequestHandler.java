package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
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
				
					String referenceType = form.get("reftype");
					ApplicantRefRequestBean abean = null;
					abean = ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("id"));
					//now we load the correct page
					//admin, guide, and teacher only available to people logged into MS and correct rights
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
					}
					else if (referenceType.equals("external")) {
						path = "nlesd_external_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("manage")) {
						path = "nlesd_manage_reference_checklist_app.jsp";
					}
					else if (referenceType.equals("support")) {
						path = "nlesd_support_reference_checklist_app.jsp";
					}
					else {
						//TODO: WILL BE EVER GET HERE???
						return "/MemberServices/memberservices.html";
					}

					request.setAttribute("arefreq", abean);
					
					request.setAttribute("PROFILE", ApplicantProfileManager.getApplicantProfileBean(abean.getApplicantId()));
				
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
