package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.colaboration.bean.CollaborationException;
import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class ListDiscussionsRequestHandler extends RequestHandlerImpl {

	public ListDiscussionsRequestHandler() {

		requiredPermissions = new String[] {
			"COLLABORATION-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				request.setAttribute("DISCUSSIONBEANS", CollaborationManager.getDiscussionBeans());

				path = "discussion_list.jsp";
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