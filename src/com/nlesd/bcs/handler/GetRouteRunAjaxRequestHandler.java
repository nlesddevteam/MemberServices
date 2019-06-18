package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunBean;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
public class GetRouteRunAjaxRequestHandler extends RequestHandlerImpl {
	public GetRouteRunAjaxRequestHandler() {
		   

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			int runid = form.getInt("runid");
			BussingContractorSystemRouteRunBean rbean = BussingContractorSystemRouteRunManager.getRouteRunById(runid);
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<RUN>");
			sb.append("<ID>" + rbean.getId()+ "</ID>");
			sb.append("<ROUTERUN>" + rbean.getRouteRun()+ "</ROUTERUN>");
			sb.append("<ROUTETIME>" + rbean.getRouteTime()+ "</ROUTETIME>");
			sb.append("<SCHOOLS>");
			for (Map.Entry<Integer, String> entry : rbean.getRunSchoolsDD().entrySet()) {
				sb.append("<SCHOOL>");
				sb.append("<SCHOOLID>" + entry.getKey() + "</SCHOOLID>");
				sb.append("<SCHOOLNAME>" + entry.getValue() + "</SCHOOLNAME>");
				sb.append("</SCHOOL>");
			}
			sb.append("</SCHOOLS>");
			sb.append("</RUN>");
			sb.append("</ROUTES>");
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
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>ERROR GETTING RUN</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
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