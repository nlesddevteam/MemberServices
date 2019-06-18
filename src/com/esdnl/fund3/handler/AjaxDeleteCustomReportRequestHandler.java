package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.dao.CustomReportManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AjaxDeleteCustomReportRequestHandler extends RequestHandlerImpl {
	public AjaxDeleteCustomReportRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer id =Integer.parseInt(form.get("reportid"));
			CustomReportManager.deleteCustomReport(id);
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<REPORTS>");
			sb.append("<REPORT>");
			sb.append("<MESSAGE>REPORTDELETED</MESSAGE>");
			sb.append("</REPORT>");
			sb.append("</REPORTS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

			
		}
		catch (Exception e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<REPORTS>");
			sb.append("<REPORT>");
			sb.append("<MESSAGE>ERROR DELETING REPORT</MESSAGE>");
			sb.append("</REPORT>");
			sb.append("</REPORTS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		return path;
	}

}