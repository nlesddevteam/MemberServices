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
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionOptionBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionOptionManager;

public class GetSectionOptionAjaxRequestHandler extends RequestHandlerImpl {
	public GetSectionOptionAjaxRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("did")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		super.handleRequest(request, response);
		String smessage="SUCCESS";
		String xml = null;
		StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				Integer did = form.getInt("did");
				//delete file first
				SchoolReviewSectionOptionBean fbean = SchoolReviewSectionOptionManager.getSchoolReviewSectionOptionById(did);
				//now we delete the file from hard driver
				sb.append("<SOOPTIONS>");
				sb.append(fbean.toXml());
				sb.append("</SOOPTIONS>");

			}catch(Exception e) {
				smessage=e.getMessage();
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
			sb.append("<SOOPTIONS>");
			sb.append("<SOOPTION>");
			sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
			sb.append("</SOOPTION>");
			sb.append("</SOOPTIONS>");
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