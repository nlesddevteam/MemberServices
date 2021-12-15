package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.Covid19SDSStatusBean;
import com.esdnl.personnel.jobs.dao.Covid19SDSStatusManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddCovid19SDSStatustAjaxRequestHandler extends RequestHandlerImpl{
	public AddCovid19SDSStatustAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-COVID19"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicantid"),new RequiredFormElement("eml")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="";
			String stext="";
			if (validate_form()) {
				try {
					//look up sds id
					//String sdsid = Covid19SDSStatusManager.getSDSId(form.get("applicantid"), form.get("eml"));
					String sdsid = Covid19SDSStatusManager.getSDSId(form.get("applicantid"), "rodneybatten@nlesd.ca");
					if(sdsid == null) {
						//can't to sds, send error back
						message="Could not link profile to SDS";
					}else {
						//good to linked
						Covid19SDSStatusBean sbean = new Covid19SDSStatusBean();
						sbean.setApplicantId(form.get("applicantid"));
						sbean.setAddedBy(usr.getLotusUserFullName());
						sbean.setSdsId(sdsid);
						Covid19SDSStatusManager.addCovid19SDSStatus(sbean);
						message="SUCCESS";
						Date t = new Date();
						DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
						stext="Special Status added on " + dt.format(t) + " by " + usr.getLotusUserFullName();
						
					}
				}
				catch (Exception e) {
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<COVID19>");
					sb.append("<CSTATUS>");
					sb.append("<MESSAGE>" +e.getMessage() + "</MESSAGE>");
					sb.append("</CSTATUS>");
					sb.append("</COVID19>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<COVID19>");
			sb.append("<CSTATUS>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<STEXT>" + stext + "</STEXT>");
			sb.append("</CSTATUS>");
			sb.append("</COVID19>");
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