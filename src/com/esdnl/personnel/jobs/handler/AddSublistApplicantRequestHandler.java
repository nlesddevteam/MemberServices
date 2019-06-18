package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddSublistApplicantRequestHandler extends RequestHandlerImpl {

	public AddSublistApplicantRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("list_id"), new RequiredFormElement("sin")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("sin"));

				if (profile != null) {
					String[] l_id = form.getArray("list_id");

					for (int i = 0; i < l_id.length; i++) {
						ApplicantProfileManager.applyForPosition(profile, SubListManager.getSubListBean(Integer.parseInt(l_id[i])));
					}

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