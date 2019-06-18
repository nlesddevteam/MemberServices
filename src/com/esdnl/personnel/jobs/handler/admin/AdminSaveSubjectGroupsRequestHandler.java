package com.esdnl.personnel.jobs.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SubjectDB;
import com.esdnl.school.bean.SubjectGroupBean;
import com.esdnl.school.database.SubjectGroupManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AdminSaveSubjectGroupsRequestHandler extends RequestHandlerImpl {

	public AdminSaveSubjectGroupsRequestHandler() {

		this.requiredRoles = new String[] {
			"ADMINISTRATOR"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("group_name")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			if (form.exists("group_id")) {
				SubjectGroupBean group = SubjectGroupManager.getSubjectGroupBean(form.getInt("group_id"));

				group.setGroupName(group.getGroupName());

				group.getSubjects().clear();
				for (Integer id : form.getIntegerArray("group_subjects")) {
					group.getSubjects().add(SubjectDB.getSubject(id));
				}

				SubjectGroupManager.updateSubjectGroupBean(group);

				request.setAttribute("groups", SubjectGroupManager.getSubjectGroupBeans());

				path = "admin_subject_groups.jsp";
			}
			else {
				SubjectGroupManager.addSubjectGroupBean(form.get("group_name"), form.getIntegerArray("group_subjects"));

				request.setAttribute("msg", "Subject group added.");

				request.setAttribute("subjects", SubjectDB.getSubjects());

				path = "admin_add_subject_group.jsp";
			}
		}

		return path;
	}

}
