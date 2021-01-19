package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionManager;

public class AddNewSectionAjaxRequestHandler extends RequestHandlerImpl{
	public AddNewSectionAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sectitle"),
				new RequiredFormElement("sectype"),
				new RequiredFormElement("secdescription"),
				new RequiredFormElement("secstatus"),
				new RequiredFormElement("secreviewid")
			});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String message="";
		int id=0;
			if (validate_form()) {
				try {
					//get fields
					SchoolReviewSectionBean srb = new SchoolReviewSectionBean();
					srb.setSecTitle(form.get("sectitle"));
					srb.setSecType(form.getInt("sectype"));
					srb.setSecStatus(form.getInt("secstatus"));
					srb.setSecDescription(form.get("secdescription"));
					srb.setSecReviewId(form.getInt("secreviewid"));
					srb.setSecAddedBy(usr.getLotusUserFullName());
					id = SchoolReviewSectionManager.addSchoolReviewSection(srb);
					message="SUCCESS";
					
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<SRSECTIONS>");
			sb.append("<RSECTION>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<SID>" + id + "</SID>");
			sb.append("<USER>" + usr.getLotusUserFullName() + "</USER>");
			sb.append("</RSECTION>");
			sb.append("</SRSECTIONS>");
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