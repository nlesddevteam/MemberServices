package com.esdnl.webmaint.esdweb.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class AddAnnouncementRequestHandler extends RequestHandlerImpl {

	public AddAnnouncementRequestHandler() {

		requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredSelectionFormElement("msg_type", -1), new RequiredFormElement("msg_date"),
				new RequiredPatternFormElement("msg_date", FormElementPattern.DATE_PATTERN),
				new RequiredFormElement("msg_header"), new RequiredFormElement("msg_body")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			if (form.hasValue("op", "CONFIRM")) {
				if (validate_form()) {
					AnnouncementBean msg = new AnnouncementBean();

					try {

						if (form.uploadFileExists("msg_img"))
							msg.setImage(save_file("msg_img", "/../../nlesdweb/WebContent/announcements/img/"));

						if (form.uploadFileExists("msg_full_story"))
							msg.setFullStoryLink(save_file("msg_full_story", "/../../nlesdweb/WebContent/announcements/doc/"));

						msg.setType(form.getInt("msg_type"));
						msg.setDate(form.getDate("msg_date"));
						msg.setHeader(form.get("msg_header"));
						msg.setBody(form.get("msg_body"));

						msg.setImageCaption(form.get("msg_img_caption"));

						msg.setShowOnFrontPage(form.exists("msg_front_page"));

						if (form.exists("school_id") && (form.getInt("school_id") > -1))
							msg.setSchool(SchoolDB.getSchool(form.getInt("school_id")));
						else
							msg.setSchool(null);

						AnnouncementManager.addAnnouncementBean(msg);

						request.setAttribute("msg", "MESSAGE ADD SUCCESSFULLY");
					}
					catch (EsdWebException e) {
						request.setAttribute("msg", e.getMessage());
					}
				}
			}

			request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(form.getInt("msg_type"), false));
			request.setAttribute("VIEW_TYPE", new Integer(form.getInt("msg_type")));
			request.setAttribute("SCHOOLS", SchoolDB.getSchools());
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}

		path = "announcements.jsp";

		return path;
	}
}