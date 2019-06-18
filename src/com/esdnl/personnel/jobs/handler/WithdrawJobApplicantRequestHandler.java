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

public class WithdrawJobApplicantRequestHandler extends RequestHandlerImpl {

	public WithdrawJobApplicantRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "MANAGER OF HR - PERSONNEL"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("uid"), new RequiredFormElement("comp_num")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("uid"));
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				ApplicantProfileManager.withdrawApplicantion(profile, job);

				// generate XML for candidate details.
				String xml = null;

				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<WITHDRAW-JOB-APPLICANT-RESPONSE>");
				sb.append("<RESPONSE>APPLICATION REMOVED SUCCESSFULLY.</RESPONSE>");
				sb.append("<APPLICANT-UID>" + profile.getUID() + "</APPLICANT-UID>");
				sb.append("</WITHDRAW-JOB-APPLICANT-RESPONSE>");

				xml = sb.toString().replaceAll("&", "&amp;");

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
		}

		return path;
	}

}
