package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddJobApplicantRequestHandler extends RequestHandlerImpl {

	public AddJobApplicantRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num"), new RequiredFormElement("sin")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("sin"));
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if ((profile != null) && (job != null)) {
					ApplicantProfileManager.applyForPosition(profile, form.get("comp_num"));

					if (form.exists("shortlisted") && form.getBoolean("shortlisted")) {
						ApplicantProfileManager.shortListApplicant(profile.getUID(), job);
					}

					if (!form.exists("ajax")) {
						session.setAttribute("JOB_APPLICANTS",
								ApplicantProfileManager.getApplicantProfileBeanByJob(form.get("comp_num")));
						session.setAttribute("JOB", JobOpportunityManager.getJobOpportunityBean(form.get("comp_num")));

						path = "admin_view_job_applicants.jsp";
					}
					else {

						// generate XML for candidate details.
						String xml = null;
						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<ADD-APPLICANT-REQ-RESPONSE>");
						sb.append("<RESPONSE-MSG>Applicant added successfully.</RESPONSE-MSG>");
						sb.append("</ADD-APPLICANT-REQ-RESPONSE>");
						xml = sb.toString().replaceAll("&", "&amp;");

						System.out.println(xml);

						PrintWriter out = response.getWriter();

						response.setContentType("text/xml");
						response.setHeader("Cache-Control", "no-cache");
						out.write(xml);
						out.flush();
						out.close();
						path = null;
					}
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not add applicant.");
				path = "admin_index.jsp";
			}
		}
		else
			System.out.println(validator.getErrorString());

		return path;
	}
}