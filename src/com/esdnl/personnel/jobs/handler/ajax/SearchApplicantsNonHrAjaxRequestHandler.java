package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantSearchNonHrBean;
import com.esdnl.personnel.jobs.dao.ApplicantSearchNonHrManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class SearchApplicantsNonHrAjaxRequestHandler extends RequestHandlerImpl {

	public SearchApplicantsNonHrAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("searchby"), new RequiredFormElement("searchfor")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		PrintWriter out = response.getWriter();
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		try {
			String searchby = form.get("searchby");
			String searchfor = form.get("searchfor");
			sb.append("<APPLIST>");
			ApplicantSearchNonHrBean[] apps = ApplicantSearchNonHrManager.searchApplicantProfile(searchby, searchfor);
			for(ApplicantSearchNonHrBean app:apps) {
				sb.append(app.toXml());
			}
			sb.append("<RESULT>SUCCESS</RESULT>");
			sb.append("</APPLIST>");
			xml = StringUtils.encodeXML(sb.toString());
			System.out.println(xml);
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}catch(Exception e) {
			sb.append("<APPLIST>");
			sb.append("<RESULT>" + e.getMessage() + "</RESULT>");
			sb.append("</APPLIST>");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}