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

public class AddUpdateRegistrationSchoolAjaxRequestHandler extends RequestHandlerImpl{
	public AddUpdateRegistrationSchoolAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-ADD-NEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("pcap"),
				new RequiredFormElement("pperiod"),
				new RequiredFormElement("pschool"),
				new RequiredFormElement("pmode"),
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
					IcfSchoolBean vbean = new IcfSchoolBean();
					vbean.setIcfSchCap(form.getInt("pcap"));
					vbean.setIcfRegPerId(form.getInt("pperiod"));
					vbean.setIcfSchSchoolId(form.getInt("pschool"));
					if(form.get("pmode").equals("A")) {
						vbean.setIcfSchId(IcfSchoolManager.addIcfSchoolBean(vbean));
						
					}else {
						vbean.setIcfSchId(form.getInt("pschid"));
						//update function
						IcfSchoolManager.updateIcfSchoolBean(vbean);
					}
					
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
				ArrayList<IcfSchoolBean> alist=IcfSchoolManager.getRegistrationPeriodSchools(form.getInt("pperiod"));
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
