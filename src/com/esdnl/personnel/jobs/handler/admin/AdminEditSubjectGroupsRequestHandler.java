package com.esdnl.personnel.jobs.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SubjectDB;
import com.esdnl.school.database.SubjectGroupManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AdminEditSubjectGroupsRequestHandler extends RequestHandlerImpl {

	public AdminEditSubjectGroupsRequestHandler() {

		this.requiredRoles = new String[] {
			"ADMINISTRATOR"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			request.setAttribute("group", SubjectGroupManager.getSubjectGroupBean(form.getInt("id")));

			request.setAttribute("subjects", SubjectDB.getSubjects());

			path = "admin_edit_subject_group.jsp";
		}
		else {
			request.setAttribute("groups", SubjectGroupManager.getSubjectGroupBeans());

			path = "admin_subject_groups.jsp";
		}
		return path;
	}

}
