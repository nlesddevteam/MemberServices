package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class SendApplicantLoginInfoEmailRequestHandler extends RequestHandlerImpl {

	public SendApplicantLoginInfoEmailRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW-PWD"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("uid")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			System.out.println("SENDING APPLICANT PASSOWRD INFO EMAIL.");

			if (validate_form()) {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("uid"));

				if (profile != null) {
					EmailBean email = new EmailBean();
					email.setTo(profile.getEmail());
					email.setSubject("NLESD Job Opportunity System - Login Information");
					email.setBody("Email: " + profile.getEmail() + "<BR><BR>Password: " + profile.getPassword());
					email.setFrom("noreply@esdnl.ca");
					email.send();

					//generate XML.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
					sb.append("<RESPONSE-MSG>SENT SUCCESSFULLY.</RESPONSE-MSG>");
					sb.append("</SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
					xml = sb.toString().replaceAll("&", "&amp;");

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
				}
				else {
					//generate XML.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
					sb.append("<RESPONSE-MSG>COULD NOT SEND EMAIL.</RESPONSE-MSG>");
					sb.append("</SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
					xml = sb.toString().replaceAll("&", "&amp;");

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
				}
			}
			else {
				//generate XML.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
				sb.append("<RESPONSE-MSG>FORM NOT VALID.</RESPONSE-MSG>");
				sb.append("</SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
				xml = sb.toString().replaceAll("&", "&amp;");

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}

			path = null;
		}
		catch (Exception e) {
			e.printStackTrace();

			//generate XML.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
			sb.append("<RESPONSE-MSG>" + e.getMessage() + "</RESPONSE-MSG>");
			sb.append("</SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
			xml = sb.toString().replaceAll("&", "&amp;");

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();

			path = null;
		}

		return path;
	}
}