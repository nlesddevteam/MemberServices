package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;

public class CheckSerialNumberContractorAjaxRequestHandler extends BCSApplicationRequestHandlerImpl {
	public CheckSerialNumberContractorAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sn")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String message="";
		if (validate_form() && !(this.sessionExpired)) {
			try {
				String sn = form.get("sn");
				boolean checkdl = BussingContractorVehicleManager.checkVehicleSerialNumber(sn);
				if(checkdl) {
					//found number
					message="Duplicate Number";
				}else {
					message="Valid Number";
				}
			}catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
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
		}else {
			if(this.sessionExpired) {
				path="contractorLogin.html?msg=Session expired, please login again.";
				return path;
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}
		}
		sb.append("<CONTRACTOR>");
		sb.append("<CONTRACTORSTATUS>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("</CONTRACTORSTATUS>");
		sb.append("</CONTRACTOR>");
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