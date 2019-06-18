package com.esdnl.personnel.jobs.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SubjectDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AdminAddSubjectGroupsRequestHandler extends RequestHandlerImpl {

	public AdminAddSubjectGroupsRequestHandler() {

		this.requiredRoles = new String[] {
			"ADMINISTRATOR"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		request.setAttribute("subjects", SubjectDB.getSubjects());

		path = "admin_add_subject_group.jsp";

		return path;
	}

}
