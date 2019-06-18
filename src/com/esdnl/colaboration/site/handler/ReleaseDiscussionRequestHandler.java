package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.colaboration.bean.CollaborationException;
import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ReleaseDiscussionRequestHandler extends RequestHandlerImpl {

	public ReleaseDiscussionRequestHandler() {

		requiredPermissions = new String[] {
			"COLLABORATION-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});

		if (validate_form()) {

			try {

				CollaborationManager.releaseDiscussionBeans(form.getInt("id"));

				request.setAttribute("DISCUSSIONBEANS", CollaborationManager.getTodaysDiscussionBeans());

				path = "admin_index.jsp";
			}
			catch (CollaborationException e) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Discussion topic could not be deleted. " + e.getMessage());

				path = "index.html";
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

			path = "index.html";
		}

		return path;
	}
}