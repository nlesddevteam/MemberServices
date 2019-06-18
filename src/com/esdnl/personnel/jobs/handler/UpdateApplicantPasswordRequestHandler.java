package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantSecurityManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class UpdateApplicantPasswordRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
	super.handleRequest(request, response);
	String  email ="";
	String  newPassword="";
	try {
		//we need to retrieve the email
		//ApplicantSecurityBean asb = ApplicantSecurityManager.getApplicantSecurityBeanByEmail(form.get("email"));
		email=form.get("email");
		newPassword=form.get("password");
		//update the password for the user
		Boolean updatePassword=ApplicantSecurityManager.updatePasswordForApplicant(email, newPassword);
		if (updatePassword)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<UPATE-APPLICANT-PASSWORD>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PASSWORDUPDATED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</UPATE-APPLICANT-PASSWORD>");
			xml = StringUtils.encodeXML(sb.toString());
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<UPATE-APPLICANT-PASSWORD>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PASSWORDUPDATEFAILED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</UPATE-APPLICANT-PASSWORD>");
			xml = StringUtils.encodeXML(sb.toString());
			System.out.println(xml);
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
		sb.append("<UPATE-APPLICANT-PASSWORD>");
		sb.append("<INFO>");
		sb.append("<MESSAGE>PASSWORDUPDATEFAILED</MESSAGE>");
		sb.append("</INFO>");
		sb.append("</UPATE-APPLICANT-PASSWORD>");
		xml = StringUtils.encodeXML(sb.toString());
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