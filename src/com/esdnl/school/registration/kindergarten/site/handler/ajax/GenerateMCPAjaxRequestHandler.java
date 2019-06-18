package com.esdnl.school.registration.kindergarten.site.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class GenerateMCPAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	public GenerateMCPAjaxRequestHandler() {

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			String mcp = KindergartenRegistrationManager.generateNextMCP();
			String expiry = "12/" + Calendar.getInstance().get(Calendar.YEAR);

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GENERATE-MCP-RESPONSE mcp='" + mcp + "' mcp-expiry='" + expiry + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (SchoolRegistrationException e) {
			e.printStackTrace(System.err);
		}

		return null;
	}

}
