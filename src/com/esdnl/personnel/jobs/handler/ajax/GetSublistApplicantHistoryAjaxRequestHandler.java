package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantSubListAuditBean;
import com.esdnl.personnel.jobs.dao.ApplicantSubListAuditManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetSublistApplicantHistoryAjaxRequestHandler extends RequestHandlerImpl {

	public GetSublistApplicantHistoryAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("appid"), new RequiredFormElement("sublistid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if(validate_form()) {
			try {
				ApplicantSubListAuditBean[] list = ApplicantSubListAuditManager.getSublistApplicantAuditTrail(form.getInt("sublistid"), form.get("appid"));

				sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SUBLISTHISTORY>");
				if(list.length  == 0) {
					sb.append("<SLENTRIES>");
					sb.append("<SLENTRY><MESSAGE>NODATA</MESSAGE></SLENTRY>");
					sb.append("</SLENTRIES>");
				}else {
					sb.append("<SLENTRIES>");
					for(ApplicantSubListAuditBean ebean: list) {
						sb.append(ebean.toXml());
					}
					sb.append("</SLENTRIES>");
				}
				sb.append("</SUBLISTHISTORY>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}catch(Exception e) {
				sb.append("<SUBLISTHISTORY>");
				sb.append("<SLENTRIES>");
				sb.append("<SLENTRY><MESSAGE>" + e.getMessage() + "</MESSAGE></SLENTRY>");
				sb.append("</SLENTRIES>");
				sb.append("</SUBLISTHISTORY>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}

			
		}else {
			sb.append("<SUBLISTHISTORY>");
			sb.append("<SLENTRIES>");
			sb.append("<SLENTRY><MESSAGE>" + this.validator.getErrorString() + "</MESSAGE></SLENTRY>");
			sb.append("</SLENTRIES>");
			sb.append("</SUBLISTHISTORY>");
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