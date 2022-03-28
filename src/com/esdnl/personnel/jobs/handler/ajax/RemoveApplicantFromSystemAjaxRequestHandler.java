package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class RemoveApplicantFromSystemAjaxRequestHandler extends RequestHandlerImpl {
	public RemoveApplicantFromSystemAjaxRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("appid")
			});
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String details="";
		String status="";
		try {
			if (validate_form()) {
				String appid = form.get("appid");
				// first we check to see if profile has any related recommendations
				//if yes then we do not delete
				if(ApplicantProfileManager.checkApplicantReommendations(appid)) {
					status="INVALID";
					details="Applicant profile has related recommendations, it cannot be deleted";
				}else {
					ApplicantProfileManager.deleteApplicantProfile(appid);
					details="Applicant profile deleted successfully";
					request.setAttribute("msgOK", "Applicant profile deleted successfully.");
					status="SUCCESS";
				}
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<APPLICANT>");
				sb.append("<PROFILE>");
				sb.append("<STATUS>" + status +"</STATUS>");
				sb.append("<DETAILS>" + details + "</DETAILS>");
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