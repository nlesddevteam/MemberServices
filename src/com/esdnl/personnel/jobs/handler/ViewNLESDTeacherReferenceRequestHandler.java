package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceTeacherBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceTeacherManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewNLESDTeacherReferenceRequestHandler extends RequestHandlerImpl {

	public ViewNLESDTeacherReferenceRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "view_nlesd_teacher_reference.jsp";

		if (validate_form()) {
			try {
				NLESDReferenceTeacherBean ref = NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(form.getInt("id"));

				if (ref != null) {
					// principal can only view if they have submited the reference or if the
					// applicant is shortlisted for a position
					// at their school.
					if (usr.checkRole("PRINCIPAL") || usr.checkRole("VICE PRINCIPAL")) {
						Personnel p = usr.checkRole("PRINCIPAL") ? usr.getPersonnel()
								: usr.getPersonnel().getSchool().getSchoolPrincipal();
						boolean authorized = false;
						if (ref.getProvidedBy().equalsIgnoreCase(p.getFullName())
								|| ref.getProvidedBy().equalsIgnoreCase(usr.getPersonnel().getFullName()))
							authorized = true;
						else {
							for (JobOpportunityBean opp : JobOpportunityManager.getJobOpportunityBeans(p.getSchool().getSchoolID())) {
								if (ApplicantProfileManager.getApplicantShortlistMap(opp).containsKey(ref.getProfile().getUID())) {
									authorized = true;
									break;
								}
							}
						}
						if (!authorized) {
							try {
								new AlertBean(new com.awsd.security.SecurityException("Applicant Reference Illegal Access Attempted By "
										+ usr.getPersonnel().getFullNameReverse()));
							}
							catch (EmailException e) {}

							throw new com.awsd.security.SecurityException("Applicant Reference Illegal Access Attempted By "
									+ usr.getPersonnel().getFullNameReverse());
						}
					}

					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}
				else {
					request.setAttribute("msg", "Invalid request.");

					path = "admin_index.jsp";
				}
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Could not view reference.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
		}
		return path;
	}

}
