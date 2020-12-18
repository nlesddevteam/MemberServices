package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.PDTravelClaimEventManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class GetTravelClaimPDItemAjaxRequestHandler extends RequestHandlerImpl {
		public GetTravelClaimPDItemAjaxRequestHandler() {
			this.requiredPermissions = new String[] {
					"TRAVEL-EXPENSE-VIEW"
			};
			this.validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("pid")
			});
		}

		public String handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws ServletException,
					IOException {

			super.handleRequest(request, response);

			Integer pdid = form.getInt("pid");
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			try {
				if (validate_form()) {
					sb.append("<PDEVENTS>");
					sb.append(PDTravelClaimEventManager.getPDEventById(pdid).toXml());
					sb.append("</PDEVENTS>");
					
				}else {
					sb.append("<PDEVENTS>");
					sb.append("<PDEVENT>");
					sb.append("<MESSAGE>" + this.validator.getErrorString() + "</PDEVENT>");
					sb.append("</PDEVENT>");
					sb.append("</PDEVENTS>");
				}
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
				sb.append("<PDEVENTS>");
				sb.append("<PDEVENT>");
				sb.append("<MESSAGE>" + e.getMessage() + "</PDEVENT>");
				sb.append("</PDEVENT>");
				sb.append("</PDEVENTS>");
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
