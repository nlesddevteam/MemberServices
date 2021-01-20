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
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewFileManager;

public class DeleteFileAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteFileAjaxRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("did"),
				new RequiredFormElement("dtype"),
				new RequiredFormElement("filename")
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
				String dtype = form.get("dtype");
				String fileName = form.get("filename");
				//delete file first
				SchoolReviewFileManager.deleteFileManager(did);
				//now we delete the file from hard driver
				if(dtype.equals("S")) {
					String filelocation = SchoolReviewFileBean.rootbasepath + "sections/files/";
					delete_file(filelocation, fileName);
				}else {
					
				}

			}catch(Exception e) {
				smessage=e.getMessage();
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<FILES>");
				sb.append("<FILE>");
				sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
				sb.append("</FILE>");
				sb.append("</FILES>");
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
		sb.append("<FILES>");
		sb.append("<FILE>");
		sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
		sb.append("</FILE>");
		sb.append("</FILES>");
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