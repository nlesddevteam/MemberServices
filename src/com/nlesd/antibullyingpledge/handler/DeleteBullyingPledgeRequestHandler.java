package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;
public class DeleteBullyingPledgeRequestHandler extends RequestHandlerImpl {
	public DeleteBullyingPledgeRequestHandler()
	{
		requiredPermissions = new String[] {
				"SURVEY-ADMIN-VIEW"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		
	super.handleRequest(request, response);
	String  id ="";
	try {
		
		id=form.get("id");
		Boolean deletePledge=AntiBullyingPledgeManager.deletePledge(Integer.parseInt(id));
		if (deletePledge)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<DELETE-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>PLEDGEDELETED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</DELETE-BULLYING-PLEDGE>");
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
			sb.append("<DELETE-BULLYING-PLEDGE>");
			sb.append("<INFO>");
			sb.append("<MESSAGE>DELETEFAILED</MESSAGE>");
			sb.append("</INFO>");
			sb.append("</DELETE-BULLYING-PLEDGE>");
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
		sb.append("<DELETE-BULLYING-PLEDGE>");
		sb.append("<INFO>");
		sb.append("<MESSAGE>DELETEFAILED</MESSAGE>");
		sb.append("</INFO>");
		sb.append("</DELETE-BULLYING-PLEDGE>");
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
