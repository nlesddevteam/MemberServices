package com.esdnl.webmaint.esdweb.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class ArchiveAnnouncementRequestHandler extends RequestHandlerImpl {

	public ArchiveAnnouncementRequestHandler() {

		this.requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("archive-id"), new RequiredFormElement("view_type")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				try {

					if (form.exists("archive-id")) {
						for (int id : form.getIntArray("archive-id"))
							AnnouncementManager.archiveAnnouncementBean(id);
					}

					request.setAttribute("edit_msg", "MESSAGE ARCHIVED SUCCESSFULLY");
				}
				catch (EsdWebException e) {
					request.setAttribute("edit_msg", e.getMessage());
				}
			}
			else
				request.setAttribute("edit_msg", validator.getErrorString());

			request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(form.getInt("view_type"), false));
			request.setAttribute("ARCHIVED_MESSAGES",
					AnnouncementManager.getAnnouncementBeans(form.getInt("view_type"), true));
			request.setAttribute("VIEW_TYPE", new Integer(form.getInt("view_type")));
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("edit_msg", e.getMessage());
		}

		path = "announcements.jsp";

		return path;
	}
}