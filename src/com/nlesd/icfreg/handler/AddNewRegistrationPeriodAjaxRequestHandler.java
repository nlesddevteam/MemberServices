package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;

public class AddNewRegistrationPeriodAjaxRequestHandler extends RequestHandlerImpl{
	public AddNewRegistrationPeriodAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-ADD-NEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("startdate"),
				new RequiredFormElement("enddate"),
				new RequiredFormElement("schoolyear")
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
					IcfRegistrationPeriodBean vbean = new IcfRegistrationPeriodBean();
					String syear = form.get("schoolyear");
					String sdate = form.get("startdate").replace("T", " ");
					String edate = form.get("enddate").replace("T", " ");;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date sdatevalue = formatter.parse(sdate);
					Date edatevalue = formatter.parse(edate);
					
					vbean.setIcfRegPerSchoolYear(syear);
					vbean.setIcfRegStartDate(sdatevalue);
					vbean.setIcfRegEndDate(edatevalue);
					IcfRegistrationPeriodManager.addIcfRegistrationPeriodBean(vbean);
				}
				catch (Exception e) {
					message=e.getMessage();
					sb.append("<RPERIODS>");
					sb.append("<RPERIOD>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RPERIOD>");
					sb.append("</RPERIODS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
				}
				
				sb.append("<RPERIODS>");
				//get new verions of all periods and return
				ArrayList<IcfRegistrationPeriodBean> alist=IcfRegistrationPeriodManager.getRegistrationPeriods();
				for(IcfRegistrationPeriodBean b :alist) {
					sb.append("<RPERIOD>");
					sb.append(b.toXml());
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RPERIOD>");
				}
				sb.append("</RPERIODS>");
			}else {
				sb.append("<RPERIODS>");
				sb.append("<RPERIOD>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</RPERIOD>");
				sb.append("</RPERIODS>");
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