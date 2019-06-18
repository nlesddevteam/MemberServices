package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.ReferenceBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;
import com.esdnl.personnel.jobs.dao.ApplicantEsdExperienceManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.personnel.jobs.dao.ReferenceManager;
import com.esdnl.servlet.DependentFormElement;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddTeacherReferenceCheckRequestHandler extends RequestHandlerImpl {

	public AddTeacherReferenceCheckRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicant_id"),

				new RequiredFormElement("ref_provider_name"), new RequiredFormElement("ref_provider_position"),

				new RequiredFormElement("Q1"), new RequiredFormElement("Q2"), new RequiredFormElement("Q3"),
				new RequiredFormElement("Q4"), new RequiredFormElement("Q5"), new RequiredFormElement("Q6"),

				new RequiredFormElement("Q7"),
				new DependentFormElement("Q7_Comment", new RequiredFormElement("Q7"), "YES"),

				new RequiredFormElement("Q8"),

				// new RequiredFormElement("Q9"),

				new RequiredFormElement("Q10"),

				new RequiredFormElement("Scale1"), new RequiredFormElement("Scale2"), new RequiredFormElement("Scale3"),
				new RequiredFormElement("Scale4"), new RequiredFormElement("Scale5"), new RequiredFormElement("Scale6"),
				new RequiredFormElement("Scale7"), new RequiredFormElement("Scale8"), new RequiredFormElement("Scale9"),
				new RequiredFormElement("Scale10")
		});

		if (form.hasValue("op", "APPLICANT_FILTER")) // AJAX CALL
		{
			ApplicantProfileBean profiles[] = null;

			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<APPLICANT-PROFILE-LIST>");
			try {
				profiles = ApplicantProfileManager.getApplicantProfileBeanByNameSearch(form.get("criteria"));

				for (ApplicantProfileBean profile : profiles)
					sb.append(profile.generateXML());
			}
			catch (JobOpportunityException e) {
				System.err.println(">>>>ERROR SEARCH FOR APPLICANT <<<<<<<<");
				e.printStackTrace(System.err);
			}

			sb.append("</APPLICANT-PROFILE-LIST>");
			xml = sb.toString().replaceAll("&", "&amp;");

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}

		else if (form.hasValue("op", "CANDIDATE_DETAILS")) // AJAX CALL
		{

			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<APPLICANT>");

			try {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("uid"));
				ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(form.get("uid"));
				ApplicantEducationOtherBean other = ApplicantEducationOtherManager.getApplicantEducationOtherBean(form.get("uid"));

				sb.append(profile.generateXML());
				if (esd_exp != null)
					sb.append(esd_exp.generateXML());
				if (other != null)
					sb.append(other.generateXML());
			}
			catch (JobOpportunityException e) {
				System.err.println(">>>>>>>> ERROR LOADING APPLICANT DETAILS <<<<<<<");
				e.printStackTrace(System.err);
			}

			sb.append("</APPLICANT>");
			xml = sb.toString().replaceAll("&", "&amp;");

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}

		else if (form.hasValue("confirm", "true") && validate_form()) {

			try {

				ReferenceBean ref = null;

				if (form.exists("reference_id"))
					ref = ReferenceManager.getReferenceBean(form.getInt("reference_id"));
				else {
					ref = new ReferenceBean();

					ref.setApplicant(ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id")));
				}

				ref.setQ1(form.get("Q1"));
				ref.setQ2(form.get("Q2"));
				ref.setQ3(form.get("Q3"));
				ref.setQ4(form.get("Q4"));
				ref.setQ5(form.get("Q5"));
				ref.setQ6(form.get("Q6"));
				ref.setQ7(form.get("Q7"));
				ref.setQ7Comment(form.get("Q7_Comment"));
				ref.setQ8(form.get("Q8"));
				ref.setQ9(form.get("Q9"));
				ref.setQ9Comment(form.get("Q9_Comment"));
				ref.setQ10(form.get("Q10"));

				ref.setScale1(form.get("Scale1"));
				ref.setScale2(form.get("Scale2"));
				ref.setScale3(form.get("Scale3"));
				ref.setScale4(form.get("Scale4"));
				ref.setScale5(form.get("Scale5"));
				ref.setScale6(form.get("Scale6"));
				ref.setScale7(form.get("Scale7"));
				ref.setScale8(form.get("Scale8"));
				ref.setScale9(form.get("Scale9"));
				ref.setScale10(form.get("Scale10"));

				ref.setReferenceProviderName(form.get("ref_provider_name"));
				ref.setReferenceProviderPosition(form.get("ref_provider_position"));

				if (ref.isNew()) {
					ref = ReferenceManager.addReferenceBean(ref);

					if (form.exists("request_id")) {
						ReferenceCheckRequestBean refchk = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("request_id"));

						if (refchk != null) {
							refchk.setReferenceId(ref.getId());
							ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refchk);
						}
					}
				}
				else {
					ReferenceManager.updateReferenceBean(ref);
				}

				request.setAttribute("REFERENCE_BEAN", ref);
				request.setAttribute("PROFILE", ref.getApplicant());

				request.setAttribute("msg", "Reference submitted successfully. Thank you!");

				path = "view_teacher_reference.jsp";

			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
				request.setAttribute("FORM", form);

				path = "add_teacher_reference.jsp";
			}
		}
		else {

			if (form.hasValue("confirm", "true")) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}

			path = "add_teacher_reference.jsp";
		}

		return path;
	}
}