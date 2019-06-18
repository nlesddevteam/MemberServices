package com.esdnl.webmaint.esdweb.handler;

import java.io.File;
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
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class EditAnnouncementRequestHandler extends RequestHandlerImpl {

	public EditAnnouncementRequestHandler() {

		this.requiredPermissions = new String[] {
				"WEBMAINTENANCE-VIEW", "WEBMAINTENANCE-ANNOUNCEMENTS"
		};

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.hasValue("op", "CONFIRM")) {

				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("id"), new RequiredFormElement("msg_type"), new RequiredFormElement("msg_date"),
						new RequiredPatternFormElement("msg_date", FormElementPattern.DATE_PATTERN),
						new RequiredFormElement("msg_header"), new RequiredFormElement("msg_body")
				});

				if (validate_form()) {

					try {
						AnnouncementBean message = AnnouncementManager.getAnnouncementBean(form.getInt("id"));

						message.setType(form.getInt("msg_type"));
						message.setDate(form.getDate("msg_date"));
						message.setHeader(form.get("msg_header"));
						message.setBody(form.get("msg_body"));

						if (form.exists("delete-image")) {
							File img = new File(ROOT_DIR + "/../ROOT/announcements/img/" + message.getImage());
							if (img.exists())
								img.delete();
							message.setImage(null);
						}

						if (form.exists("delete-pdf")) {
							File pdf = new File(ROOT_DIR + "/../ROOT/announcements/doc/" + message.getFullStoryLink());
							if (pdf.exists())
								pdf.delete();
							message.setFullStoryLink(null);
						}

						if (form.uploadFileExists("msg_img"))
							message.setImage(save_file("msg_img", "/../ROOT/announcements/img/"));

						if (form.uploadFileExists("msg_full_story"))
							message.setFullStoryLink(save_file("msg_full_story", "/../ROOT/announcements/doc/"));

						message.setImageCaption(form.get("msg_img_caption"));

						message.setShowOnFrontPage(form.exists("msg_front_page"));

						message.setArchived(form.exists("msg_archived"));

						if (form.exists("school_id") && (form.getInt("school_id") > -1))
							message.setSchool(SchoolDB.getSchool(form.getInt("school_id")));
						else
							message.setSchool(null);

						AnnouncementManager.updateAnnouncementBean(message);

						request.setAttribute("msg", "MESSAGE UPDATED SUCCESSFULLY");
						request.setAttribute("MESSAGES",
								AnnouncementManager.getAnnouncementBeans(message.getType().getTypeID(), false));
						request.setAttribute("VIEW_TYPE", new Integer(message.getType().getTypeID()));
						request.setAttribute("SCHOOLS", SchoolDB.getSchools());
					}
					catch (EsdWebException e) {
						e.printStackTrace();
						request.setAttribute("msg", e.getMessage());
					}
				}
				else
					request.setAttribute("msg", validator.getErrorString());
			}
			else {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("id"), new RequiredFormElement("type")
				});

				if (validate_form()) {
					try {
						request.setAttribute("message", AnnouncementManager.getAnnouncementBean(form.getInt("id")));
						request.setAttribute("MESSAGES", AnnouncementManager.getAnnouncementBeans(form.getInt("type"), false));
						request.setAttribute("VIEW_TYPE", new Integer(form.getInt("type")));
						request.setAttribute("SCHOOLS", SchoolDB.getSchools());
					}
					catch (EsdWebException e) {
						request.setAttribute("msg", e.getMessage());
					}
				}
				else
					request.setAttribute("msg", validator.getErrorString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}

		path = "announcements.jsp";

		return path;
	}
}