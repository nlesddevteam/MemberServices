package com.esdnl.webmaint.esdweb.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class ViewAnnouncementsRequestHandler extends RequestHandlerImpl {

	public ViewAnnouncementsRequestHandler() {

		requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (form.exists("type")) {
			request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(form.getInt("type"), false));
			request.setAttribute("VIEW_TYPE", new Integer(form.getInt("type")));
		}

		request.setAttribute("SCHOOLS", SchoolDB.getSchools());

		path = "announcements.jsp";

		return path;
	}
}