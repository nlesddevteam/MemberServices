package com.esdnl.school.registration.kindergarten.site.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetSchoolIdByDeptIdAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	public GetSchoolIdByDeptIdAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("deptid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			School school = SchoolDB.getSchoolFromDeptId(form.getInt("deptid"));

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-SCHOOL-BY-DEPTID-RESPONSE>");
			if (school != null)
				sb.append(school.toXML());
			sb.append("</GET-SCHOOL-BY-DEPTID-RESPONSE>");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (SchoolException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-SCHOOL-BY-DEPTID-RESPONSE error='No school found' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}
