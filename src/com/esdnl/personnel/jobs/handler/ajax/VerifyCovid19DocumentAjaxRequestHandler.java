package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantCovid19LogManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class VerifyCovid19DocumentAjaxRequestHandler extends RequestHandlerImpl {

	public VerifyCovid19DocumentAjaxRequestHandler() {
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
				int rid = form.getInt("id");
				
				ApplicantCovid19LogManager.verifyCovid19Doc(rid,usr.getLotusUserFullName());
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<COVID19>");
				sb.append("<VERIFY>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("<VBY>" + usr.getLotusUserFullName() + "</VBY>");
				Date d = new Date();
				DateFormat dt = new SimpleDateFormat("dd-MMMM-yyyy"); 
				sb.append("<VDATE>" + dt.format(d) + "</VDATE>");
				sb.append("</VERIFY>");
				sb.append("</COVID19>");
				
				xml = StringUtils.encodeXML(sb.toString());

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</REQUESTTOHIRE>");
				sb.append("/<RTH>");

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