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
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;

public class DeleteSchoolReviewAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteSchoolReviewAjaxRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "SCHOOL-REVIEW-ADMIN"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		super.handleRequest(request, response);
		String smessage="SUCCESS";
		if (validate_form()) {
			try {
				Integer rid = form.getInt("rid");
				//delete file first
				SchoolReviewManager.deleteSchoolReview(rid);
				}catch(Exception e) {
				smessage=e.getMessage();
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SREVIEW>");
				sb.append("<REVIEW>");
				sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
				sb.append("</REVIEW>");
				sb.append("</SREVIEW>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}else {
			smessage=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<SREVIEW>");
		sb.append("<REVIEW>");
		sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
		sb.append("</REVIEW>");
		sb.append("</SREVIEW>");
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