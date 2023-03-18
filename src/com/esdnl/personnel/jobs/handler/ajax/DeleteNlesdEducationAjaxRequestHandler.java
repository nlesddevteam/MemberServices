package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantEducationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteNlesdEducationAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteNlesdEducationAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-ADD-EDUCATION"
		};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("eid")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		try {
			if (validate_form()) {
				int dvalue = form.getInt("eid");
				ApplicantEducationManager.deleteApplicantEducationNLESDBean(dvalue);
				sb.append("<APPLICANT>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("</APPLICANT>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}else {
				sb.append("<APPLICANT>");
				sb.append("<STATUS>" + this.validator.getErrorString() + "</STATUS>");
				sb.append("</APPLICANT>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
				
			}
			catch (Exception e) {
				
				sb.append("<APPLICANT>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</APPLICANT>");
				xml = StringUtils.encodeXML(sb.toString());
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