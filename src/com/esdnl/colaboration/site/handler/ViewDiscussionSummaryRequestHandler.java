package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.colaboration.bean.CollaborationException;
import com.esdnl.colaboration.bean.DiscussionBean;
import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewDiscussionSummaryRequestHandler extends RequestHandlerImpl {

	public ViewDiscussionSummaryRequestHandler() {

		requiredPermissions = new String[] {
				"COLLABORATION-ADMIN-VIEW", "COLLABORATION-GROUP-VIEW"
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
			DiscussionBean bean = null;

			try {

				bean = CollaborationManager.getDiscussionBean(form.getInt("id"));

				if (usr.checkPermission("COLLABORATION-GROUP-VIEW") && !bean.isReleased()) {
					path = "index.html";
				}
				else {
					request.setAttribute("DISCUSSIONBEAN", bean);
					path = "discussion_summary.jsp";
				}
			}
			catch (CollaborationException e) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Discussion topic could not be added. " + e.getMessage());

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