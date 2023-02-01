package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.icfreg.bean.IcfSchoolBean;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class DeletePeriodSchoolAjaxRequestHandler extends RequestHandlerImpl{
	public DeletePeriodSchoolAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-DELETE-PERIOD"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("pid"),
				new RequiredFormElement("sid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
				try {
					//delete
					IcfSchoolManager.deletePeriodSchool(form.getInt("pid"), form.getInt("sid"));
				}
				catch (Exception e) {
					message=e.getMessage();
					sb.append("<RSCHOOLS>");
					sb.append("<RSCHOOL>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RSCHOOL>");
					sb.append("</RSCHOOLS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
				}
				
				sb.append("<RSCHOOLS>");
				//get all schools for period
				ArrayList<IcfSchoolBean> alist=IcfSchoolManager.getRegistrationPeriodSchools(form.getInt("pid"));
				for(IcfSchoolBean b :alist) {
					sb.append("<RSCHOOL>");
					sb.append(b.toXml());
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RSCHOOL>");
				}
				sb.append("</RSCHOOLS>");
			}else {
				sb.append("<RSCHOOLS>");
				sb.append("<RSCHOOL>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</RSCHOOL>");
				sb.append("</RSCHOOLS>");
			}

			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}
