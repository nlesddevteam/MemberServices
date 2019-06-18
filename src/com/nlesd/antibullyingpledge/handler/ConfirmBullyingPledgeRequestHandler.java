package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;
public class ConfirmBullyingPledgeRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
	super.handleRequest(request, response);
	String  rid ="";
	try {
		rid = form.get("rid");
		Boolean confirmPledge=AntiBullyingPledgeManager.confirmPledge(rid);
		if (confirmPledge)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONFIRM-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PLEDGE CONFIRMED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</CONFIRM-BULLYING-PLEDGE>");
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
			sb.append("<CONFIRM-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PLEDGE CONFIRMATION FAILED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</CONFIRM-BULLYING-PLEDGE>");
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
		sb.append("<CONFIRM-BULLYING-PLEDGE>");
		sb.append("<INFO>");
		sb.append("<MESSAGE>PLEDGE CONFIRMATION FAILED</MESSAGE>");
		sb.append("</INFO>");
		sb.append("</CONFIRM-BULLYING-PLEDGE>");
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
