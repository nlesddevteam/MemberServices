package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteApplicantProfileRequestHandler extends RequestHandlerImpl {

	public DeleteApplicantProfileRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("uid")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		path = "admin_index.jsp";

		if (validate_form()) {
			try {
				// first we check to see if profile has any related recommendations
				//if yes then we do not delete
				if(ApplicantProfileManager.checkApplicantReommendations(form.get("uid"))) {
					request.setAttribute("msgERR", "Applicant profile has related recommendations, it cannot be deleted.");
				}else {
					ApplicantProfileManager.deleteApplicantProfile(form.get("uid"));
					request.setAttribute("msgOK", "Applicant profile deleted successfully.");
				}
				
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);

				request.setAttribute("FORM", form);
				request.setAttribute("msgERR", "Could not delete profile.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msgERR", StringUtils.encodeHTML(validator.getErrorString()));
		}

		return path;
	}
}
