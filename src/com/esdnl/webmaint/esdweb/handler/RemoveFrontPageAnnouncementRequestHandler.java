package com.esdnl.webmaint.esdweb.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class RemoveFrontPageAnnouncementRequestHandler extends RequestHandlerImpl {

	public RemoveFrontPageAnnouncementRequestHandler() {

		requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			if (validate_form()) {
				try {
					AnnouncementManager.removeFrontPageAnnouncementBean(form.getInt("id"));

					AnnouncementBean message = AnnouncementManager.getAnnouncementBean(form.getInt("id"));
					request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(form.getInt("id"), false));
					request.setAttribute("VIEW_TYPE", new Integer(message.getType().getTypeID()));

					request.setAttribute("edit_msg", "MESSAGE REMOVED FROM FRONT PAGE SUCCESSFULLY");
				}
				catch (EsdWebException e) {
					request.setAttribute("edit_msg", e.getMessage());
				}
			}
			else
				request.setAttribute("edit_msg", validator.getErrorString());

			request.setAttribute("SCHOOLS", SchoolDB.getSchools());
		}
		catch (Exception e) {
			request.setAttribute("edit_msg", e.getMessage());
		}

		path = "announcements.jsp";

		return path;
	}
}