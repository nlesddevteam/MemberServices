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

public class AddDiscussionRequestHandler extends RequestHandlerImpl {

	public AddDiscussionRequestHandler() {

		requiredPermissions = new String[] {
			"COLLABORATION-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "add_discussion.jsp";

		if (StringUtils.isEqual(form.get("op"), "CONFIRM")) {
			validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("discussion_date"), new RequiredFormElement("discussion_title")
			});

			if (validate_form()) {
				DiscussionBean bean = new DiscussionBean();

				bean.setDiscussionDate(form.getDate("discussion_date"));
				bean.setTitle(form.get("discussion_title"));
				bean.setDescription(form.get("discussion_description"));

				try {
					CollaborationManager.addDiscussionBean(bean);
					request.setAttribute("msg", "Discussion topic added successfully.");
				}
				catch (CollaborationException e) {
					request.setAttribute("FORM", form);
					request.setAttribute("msg", "Discussion topic could not be added. " + e.getMessage());
				}
			}
			else {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
		}

		return path;
	}
}