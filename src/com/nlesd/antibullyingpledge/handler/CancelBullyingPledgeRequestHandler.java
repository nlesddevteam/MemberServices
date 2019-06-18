package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;

public class CancelBullyingPledgeRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
	super.handleRequest(request, response);
	String  rid ="";
	try {
		//we need to retrieve the email
		//ApplicantSecurityBean asb = ApplicantSecurityManager.getApplicantSecurityBeanByEmail(form.get("email"));
		rid = form.get("rid");
		//update the password for the user
		Boolean cancelPledge=AntiBullyingPledgeManager.cancelPledge(rid);
		if (cancelPledge)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CANCEL-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PLEDGE DELETED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</CANCEL-BULLYING-PLEDGE>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CANCEL-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PLEDGE DELETION FAILED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</CANCEL-BULLYING-PLEDGE>");
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
		sb.append("<CANCEL-BULLYING-PLEDGE>");
		sb.append("<INFO>");
		sb.append("<MESSAGE>PLEDGE DELETION FAILED</MESSAGE>");
		sb.append("</INFO>");
		sb.append("</CANCEL-BULLYING-PLEDGE>");
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