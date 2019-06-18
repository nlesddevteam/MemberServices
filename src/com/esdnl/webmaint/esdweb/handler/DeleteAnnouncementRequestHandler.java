package com.esdnl.webmaint.esdweb.handler;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class DeleteAnnouncementRequestHandler extends RequestHandlerImpl {

	public DeleteAnnouncementRequestHandler() {

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
					AnnouncementBean msg = AnnouncementManager.getAnnouncementBean(form.getInt("id"));

					if (msg == null)
						request.setAttribute("edit_msg", "Announcement COULD NOT BE FOUND.");
					else {
						AnnouncementManager.deleteAnnouncementBean(msg.getID());

						if (!StringUtils.isEmpty(msg.getImage())) {
							File f = new File(ROOT_DIR + "/../ROOT/images/" + msg.getImage());

							if (f.exists())
								f.delete();
						}

						if (!StringUtils.isEmpty(msg.getFullStoryLink())) {
							File f = new File(ROOT_DIR + "/../ROOT/pdf/" + msg.getFullStoryLink());

							if (f.exists())
								f.delete();
						}

						request.setAttribute("edit_msg", "Announcement DELETED SUCCESSFULLY");
						request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(msg.getType().getTypeID(), false));
						request.setAttribute("VIEW_TYPE", new Integer(msg.getType().getTypeID()));
					}
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