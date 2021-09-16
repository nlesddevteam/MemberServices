package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.esdnl.personnel.jobs.bean.SchoolEmployeeBean;
import com.esdnl.personnel.jobs.dao.SchoolEmployeeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewEmployeesByLocationAjaxRequestHandler extends RequestHandlerImpl {

	public ViewEmployeesByLocationAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("locationid")
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
			String locationid = form.get("locationid");
			//String[] testyear = Utils.getCurrentSchoolYear().split("-");
			//StringBuilder sbyear = new StringBuilder();
			//sbyear.append(testyear[0]);
			//sbyear.append("-");
			//String test = testyear[1];
			//sb.append(test.substring(2, 4));
			ArrayList<SchoolEmployeeBean> plist = SchoolEmployeeManager.getPermEmployees(com.esdnl.personnel.v2.utils.StringUtils.getCurrentSchoolYear("yyyy", "yy", "-"), locationid);
			ArrayList<SchoolEmployeeBean> vlist = SchoolEmployeeManager.getVacEmployees(com.esdnl.personnel.v2.utils.StringUtils.getCurrentSchoolYear("yyyy", "yy", "-"), locationid);
			sb.append("<EMPLOYEES>");
			for(SchoolEmployeeBean p: plist) {
				sb.append(p.toXmlPerm());
			}
			for(SchoolEmployeeBean v: vlist) {
				sb.append(v.toXmlVac());
			}
			sb.append("<MESSAGE>FOUND</MESSAGE>");
			sb.append("</EMPLOYEES>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			sb = new StringBuffer();
			sb.append("<EMPLOYEES>");
			sb.append("<MESSAGE>" + e.getMessage() +"</MESSAGE>");
			sb.append("</EMPLOYEES>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		}else {
			sb = new StringBuffer();
			sb.append("<EMPLOYEES>");
			sb.append("<MESSAGE>" + this.validator.getErrorString() +"</MESSAGE>");
			sb.append("</EMPLOYEES>");
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
