package com.esdnl.personnel.jobs.handler.admin.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolException;
import com.esdnl.school.bean.SubjectGroupBean;
import com.esdnl.school.database.SubjectGroupManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetSubjectGroupSubjectsAjaxRequestHandler extends RequestHandlerImpl {

	public GetSubjectGroupSubjectsAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("groupids")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			ArrayList<SubjectGroupBean> groups = new ArrayList<SubjectGroupBean>();

			System.out.println("GetSubjectGroupSubjectsAjaxRequestHandler ::: groupids = " + form.get("groupids[]"));

			for (String id : form.get("groupids[]").split(",")) {
				groups.add(SubjectGroupManager.getSubjectGroupBean(Integer.parseInt(id.trim())));
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-SUBJECT-GROUP-SUBJECTS-RESPONSE>");
			for (SubjectGroupBean group : groups) {
				sb.append(group.toXML());
			}
			sb.append("</GET-SUBJECT-GROUP-SUBJECTS-RESPONSE>");

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
			e.printStackTrace(System.err);
		}

		return null;
	}

}
