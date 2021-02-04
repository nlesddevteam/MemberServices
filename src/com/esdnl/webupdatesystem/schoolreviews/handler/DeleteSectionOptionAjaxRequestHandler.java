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
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionOptionManager;

public class DeleteSectionOptionAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteSectionOptionAjaxRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "SCHOOL-REVIEW-ADMIN"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("did"),
				new RequiredFormElement("dtype")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		super.handleRequest(request, response);
		String smessage="SUCCESS";
		if (validate_form()) {
			try {
				Integer did = form.getInt("did");
				//delete file first
				SchoolReviewSectionOptionManager.deleteSectionOption(did);
			}catch(Exception e) {
				smessage=e.getMessage();
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SOOPTIONS>");
				sb.append("<SOOPTION>");
				sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
				sb.append("</SOOPTION>");
				sb.append("</SOOPTIONS>");
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
		sb.append("<SOOPTIONS>");
		sb.append("<SOOPTION>");
		sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
		sb.append("</SOOPTION>");
		sb.append("</SOOPTIONS>");
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