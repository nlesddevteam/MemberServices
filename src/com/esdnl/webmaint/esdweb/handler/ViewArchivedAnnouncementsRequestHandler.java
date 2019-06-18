package com.esdnl.webmaint.esdweb.handler;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class ViewArchivedAnnouncementsRequestHandler extends RequestHandlerImpl {

	public ViewArchivedAnnouncementsRequestHandler() {

		requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		AnnouncementTypeConstant type = null;
		if (form.exists("type"))
			type = AnnouncementTypeConstant.get(form.getInt("type"));

		School school = null;
		if (form.exists("school_id"))
			school = SchoolDB.getSchool(form.getInt("school_id"));

		TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>> years = AnnouncementManager.getArchivedAnnouncementBeansMap(
				type, school);

		TreeMap<String, Vector<AnnouncementBean>> year;
		if (!form.exists("year")) {
			request.setAttribute("YEAR", years.firstKey());

			year = years.get(years.firstKey());
			request.setAttribute("MONTH", year.lastKey());
			request.setAttribute("MESSAGES", year.get(year.lastKey()));
		}
		else {
			request.setAttribute("YEAR", form.get("year"));

			year = years.get(form.get("year"));
			if (!form.exists("month")) {
				request.setAttribute("MONTH", year.firstKey());
				request.setAttribute("MESSAGES", year.get(year.firstKey()));
			}
			else {
				request.setAttribute("MONTH", form.get("month"));
				request.setAttribute("MESSAGES", year.get(form.get("month")));
			}
		}

		request.setAttribute("YEARS", years.keySet());
		request.setAttribute("MONTHS", year.keySet());
		if (type != null)
			request.setAttribute("VIEW_TYPE", new Integer(type.getTypeID()));
		if (school != null)
			request.setAttribute("VIEW_SCHOOL", school);

		request.setAttribute("SCHOOLS", SchoolDB.getSchools());

		path = "archived_announcements.jsp";

		return path;
	}
}