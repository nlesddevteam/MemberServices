package com.awsd.admin.apps.categories.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.admin.apps.categories.dao.CategoriesManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class RemoveCategoryRoleAjaxRequestHandler extends RequestHandlerImpl{
	public RemoveCategoryRoleAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"MEMBERADMIN-VIEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("catid"),new RequiredFormElement("rolename")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="";
			if (validate_form()) {
				try {
					CategoriesManager.removeRoleFromCategory(form.getInt("catid"), form.get("rolename"));
					message="SUCCESS";
				}
				catch (Exception e) {
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<CAT>");
					sb.append("<CATSTATUS>");
					sb.append("<MESSAGE>" +e.getMessage() + "</MESSAGE>");
					sb.append("</CATSTATUS>");
					sb.append("</CAT>");
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
			sb.append("<CAT>");
			sb.append("<CATSTATUS>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("</CATSTATUS>");
			sb.append("</CAT>");
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
