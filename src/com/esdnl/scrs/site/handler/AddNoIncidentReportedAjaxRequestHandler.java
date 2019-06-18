package com.esdnl.scrs.site.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.BullyingNoIncidentReportedBean;
import com.esdnl.scrs.service.BullyingNoIncidentReportedService;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddNoIncidentReportedAjaxRequestHandler extends RequestHandlerImpl {

	public AddNoIncidentReportedAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
			"BULLYING-ANALYSIS-SCHOOL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			BullyingNoIncidentReportedBean bean = new BullyingNoIncidentReportedBean();

			bean.setSchool(usr.getPersonnel().getSchool());

			BullyingNoIncidentReportedService.addBullyingNoIncidentReportedBean(bean);

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<NO-BULLYING-INCIDENT-REPORTED-RESULT>No incidents reported successfully</NO-BULLYING-INCIDENT-REPORTED-RESULT>");
			xml = sb.toString().replaceAll("&", "&amp;");

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (BullyingException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<NO-BULLYING-INCIDENT-REPORTED-RESULT>Error: " + e.getMessage()
					+ "</NO-BULLYING-INCIDENT-REPORTED-RESULT>");
			xml = sb.toString().replaceAll("&", "&amp;");

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}
