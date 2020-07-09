package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class SendAllApplicantLoginInfoEmailRequestHandler extends RequestHandlerImpl {

	public SendAllApplicantLoginInfoEmailRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW-PWD"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			ApplicantProfileBean[] profile = ApplicantProfileManager.getApplicantProfileBeans();

			if (profile.length > 0) {
				EmailBean email = null;

				for (int i = 0; i < profile.length; i++) {
					try {
						email = new EmailBean();
						email.setTo(profile[i].getEmail());
						email.setSubject("NLESD Job Opportuntity System - Login Information");
						email.setBody("Email: " + profile[i].getEmail() + "<BR><BR>Password: " + profile[i].getPassword());
						email.setFrom("noreply@nlesd.ca");
						email.send();
					}
					catch (Exception e) {
						System.err.println("COULD NOT SEND APPLICANT INFO EMAIL TO " + profile[i].getFullNameReverse() + " DUE TO "
								+ e.getMessage());
						try {
							new AlertBean(e).send();
						}
						catch (Exception ex) {}
					}
				}

				//generate XML.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SEND-APPL-LOGIN-INFO-REQ-RESPONSE>");
				sb.append("<RESPONSE-MSG>" + profile.length + " EMAILS QUEUED TO BE SENT.</RESPONSE-MSG>");
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
				sb.append("<RESPONSE-MSG>NO APPLICANTS FOUND.</RESPONSE-MSG>");
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