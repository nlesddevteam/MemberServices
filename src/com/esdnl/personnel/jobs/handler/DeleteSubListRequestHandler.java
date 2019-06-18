package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteSubListRequestHandler extends RequestHandlerImpl {

	public DeleteSubListRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-CREATE-SUBLIST"
		};
	}

	public String handleRequest(HttpServletRequest request,
															HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("list_id")
			});

			if (validate_form()) {

				SubListManager.deleteSubListBean(form.getInt("list_id"));

				request.setAttribute("msg", "Substitute list has been deleted.");

				path = "admin_view_sub_lists.jsp";
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg",
						StringUtils.encodeHTML(validator.getErrorString()));

				path = "admin_view_sub_lists.jsp";
			}
		}

		catch (JobOpportunityException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ex) {}

			request.setAttribute("msg", "Could not create sublist see administrator.");

			path = "admin_view_sub_lists.jsp";
		}

		return path;
	}
}