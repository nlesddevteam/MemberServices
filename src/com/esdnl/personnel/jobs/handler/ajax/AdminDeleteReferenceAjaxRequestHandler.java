package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AdminDeleteReferenceAjaxRequestHandler extends RequestHandlerImpl {
	public AdminDeleteReferenceAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("delid", "ID required for deletion")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			if (validate_form()) {
				int dvalue = form.getInt("delid");
				//delete the supervisor
				ApplicantSupervisorManager.deleteApplicantSupervisorBean(dvalue);
				//delete any linked ref requests
				ApplicantRefRequestManager.deleteReferenceRequestBySupervisor(dvalue);
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<APPLICANT>");
				sb.append("<PROFILE>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("</PROFILE>");
				sb.append("</APPLICANT>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<APPLICANT>");
				sb.append("<PROFILE>");
				sb.append("<STATUS>" + this.validator.getErrorString() + "</STATUS>");
				sb.append("</PROFILE>");
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
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<APPLICANT>");
				sb.append("<PROFILE>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</PROFILE>");
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