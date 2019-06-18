package com.esdnl.personnel.jobs.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.database.SubjectGroupManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AdminViewSubjectGroupsRequestHandler extends RequestHandlerImpl {

	public AdminViewSubjectGroupsRequestHandler() {

		this.requiredRoles = new String[] {
			"ADMINISTRATOR"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		request.setAttribute("groups", SubjectGroupManager.getSubjectGroupBeans());

		path = "admin_subject_groups.jsp";

		return path;
	}

}
