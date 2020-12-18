package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.Role;
import com.awsd.security.RoleDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddApprovedRateMemberAjaxRequestHandler extends RequestHandlerImpl {

	public AddApprovedRateMemberAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN"
		};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("ids")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String msg = "ADDED";
		if (validate_form()) {
			try {
				String[] ids= form.get("ids").split(",");
				Role r = RoleDB.getRole("TRAVELCLAIM APPROVED RATE");
				
				for(String s: ids) {
					//remove them from the group
					RoleDB.addRoleMembership(r,PersonnelDB.getPersonnel(Integer.parseInt(s)));
				}
			}catch (Exception e) {
				msg=e.getMessage();
				sb.append("<TRAVELCLAIMS>");
				sb.append("<TRAVELCLAIM>");
				sb.append("<MESSAGE>" + msg + "</MESSAGE>");
				sb.append("</TRAVELCLAIM>");
				sb.append("</TRAVELCLAIMS>");
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
			msg=validator.getErrorString();
		}
		
		sb.append("<TRAVELCLAIMS>");
		sb.append("<TRAVELCLAIM>");
		sb.append("<MESSAGE>" + msg + "</MESSAGE>");
		sb.append("</TRAVELCLAIM>");
		sb.append("</TRAVELCLAIMS>");
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