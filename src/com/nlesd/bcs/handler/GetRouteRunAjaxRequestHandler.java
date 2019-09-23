package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunBean;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
public class GetRouteRunAjaxRequestHandler extends RequestHandlerImpl {
	public GetRouteRunAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("runid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				int runid = form.getInt("runid");
				BussingContractorSystemRouteRunBean rbean = BussingContractorSystemRouteRunManager.getRouteRunById(runid);
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
			}
			catch (Exception e) {
				// generate XML for candidate details.
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
		}else {
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
		}
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		path = null;
		return path;
	}

}