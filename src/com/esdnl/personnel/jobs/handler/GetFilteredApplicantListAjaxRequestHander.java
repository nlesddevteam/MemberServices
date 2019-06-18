package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class GetFilteredApplicantListAjaxRequestHander extends RequestHandlerImpl {

	public GetFilteredApplicantListAjaxRequestHander() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("filter")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String filtertype=form.get("filtertype");
				String issupport="";
				if(filtertype.equals("Y")){
					issupport="S";
				}else{
					issupport="T";
				}
				ApplicantProfileBean[] beans = ApplicantProfileManager.filterApplicantProfileBeansSS(form.get("filter"),issupport);

				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<FILTERED-APPLICANTS-REQ-RESPONSE>");
				sb.append("<FILTERED-APPLICANTS>");
				for (int i = 0; i < beans.length; i++)
					sb.append(beans[i].generateXML());
				sb.append("</FILTERED-APPLICANTS>");
				sb.append("</FILTERED-APPLICANTS-REQ-RESPONSE>");
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
			catch (JobOpportunityException e) {
				e.printStackTrace();

				path = null;
			}
		}

		return path;
	}
}
