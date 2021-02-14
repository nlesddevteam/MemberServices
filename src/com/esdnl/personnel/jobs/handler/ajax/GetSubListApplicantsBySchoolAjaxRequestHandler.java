package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetSubListApplicantsBySchoolAjaxRequestHandler extends RequestHandlerImpl {

	public GetSubListApplicantsBySchoolAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sid"),new RequiredFormElement("lid")
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
			int sid = form.getInt("sid");
			int lid = form.getInt("lid");
			ApplicantProfileBean[] list = ApplicantProfileManager.getApplicantShortlistBySchool(lid, sid);
			sb.append("<APPLICANTS>");
			for(ApplicantProfileBean app : list) {
				sb.append("<PROFILE>");
				sb.append("<FIRSTNAME>" + app.getFirstname() + "</FIRSTNAME>");
				sb.append("<LASTNAME>" + app.getSurname() + "</LASTNAME>");
				String majors ="";
				if(app.getMajorsList() == null) {
					majors = "Major(s): None listed.";
				}else {
					majors = "Major(s): " + app.getMajorsList();
				}
				if(app.getMinorsList() == null) {
					majors += "\nMinor(s): None listed."; 
				}else {
					majors += "\nMinor(s): " + app.getMinorsList(); 
				}
				sb.append("<MAJORS>" + majors + "</MAJORS>");
				sb.append("<EMAIL>" + app.getEmail() + "</EMAIL>");
				sb.append("<COMMUNITY>" + app.getAddress2() + "</COMMUNITY>");
				String ph = "";
				if(app.getHomephone() != null) {
					ph = "(h) " + app.getHomephone();
				}else {
					ph = "(h) None listed.";
				}
				if(app.getCellphone() != null) {
					ph += "\n(c) " + app.getCellphone(); 
				}else {
					ph += "\n(c) None listed.";
				}
				sb.append("<PHONE>" + ph  + "</PHONE>");
				sb.append("<APPID>" + app.getSIN() + "</APPID>");
				sb.append("</PROFILE>");
				
			}
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</APPLICANTS>");
			
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
			sb.append("<APPLICANTS>");
			sb.append("<PROFILE>");
			sb.append("<MESSAGE>" + e.getMessage() +"</MESSAGE>");
			sb.append("</PROFILE>");
			sb.append("</APPLICANTS>");
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
			sb.append("<APPLICANTS>");
			sb.append("<PROFILE>");
			sb.append("<MESSAGE>" + this.validator.getErrorString() +"</MESSAGE>");
			sb.append("</PROFILE>");
			sb.append("</APPLICANTS>");
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
