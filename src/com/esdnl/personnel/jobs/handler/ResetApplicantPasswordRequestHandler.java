package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityBean;
import com.esdnl.personnel.jobs.dao.ApplicantSecurityManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class ResetApplicantPasswordRequestHandler  extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
	super.handleRequest(request, response);
	try {
		ApplicantSecurityBean asb = ApplicantSecurityManager.getApplicantSecurityBeanByEmail(form.get("email"));
		if (asb != null)
		{
			if(asb.getSecurity_question() != null) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-APPLICANT-SECURITY>");
				sb.append("<INFO>");
				sb.append("<SQUESTION>" + asb.getSecurity_question() + "</SQUESTION>");
				sb.append("<SANSWER>" + asb.getSecurity_answer() + "</SANSWER>");
				sb.append("<SEMAIL>" + form.get("email") + "</SEMAIL>");
				sb.append("<ERROR>No Error</ERROR>");
				sb.append("</INFO>");
				sb.append("</GET-APPLICANT-SECURITY>");
				xml = StringUtils.encodeXML(sb.toString());
				System.out.println(xml);
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-APPLICANT-SECURITY>");
				sb.append("<INFO>");
				sb.append("<ERROR>");
				sb.append("No security question setup for this profile, you cannot reset your password. Please contact support.");
				sb.append("</ERROR>");
				sb.append("</INFO>");
				sb.append("</GET-APPLICANT-SECURITY>");
				xml = StringUtils.encodeXML(sb.toString());
				System.out.println(xml);
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			
		}else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-APPLICANT-SECURITY>");
			sb.append("<INFO>");
			sb.append("<ERROR>");
			sb.append("We could not find the email in our system. Please create a profile or contact support if you think you have an existing profile.");
			sb.append("</ERROR>");
			sb.append("</INFO>");
			sb.append("</GET-APPLICANT-SECURITY>");
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
		sb.append("<GET-APPLICANT-SECURITY>");
		sb.append("<INFO>");
		sb.append("<ERROR>");
		sb.append("There was an error finding your email.");
		sb.append("</ERROR>");
		sb.append("</INFO>");
		sb.append("</GET-APPLICANT-SECURITY>");
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