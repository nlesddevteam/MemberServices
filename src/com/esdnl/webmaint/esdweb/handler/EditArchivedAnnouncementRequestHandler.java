package com.esdnl.webmaint.esdweb.handler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.DateUtils;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;

public class EditArchivedAnnouncementRequestHandler extends RequestHandlerImpl {

	public EditArchivedAnnouncementRequestHandler() {

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
							File img = new File(ROOT_DIR + "/../../nlesdweb/WebContent/announcements/img/" + message.getImage());
							if (img.exists())
								img.delete();
							message.setImage(null);
						}

						if (form.exists("delete-pdf")) {
							File pdf = new File(ROOT_DIR + "/../../nlesdweb/WebContent/announcements/doc/"
									+ message.getFullStoryLink());
							if (pdf.exists())
								pdf.delete();
							message.setFullStoryLink(null);
						}

						if (form.uploadFileExists("msg_img"))
							message.setImage(save_file("msg_img", "/../../nlesdweb/WebContent/announcements/img/"));

						if (form.uploadFileExists("msg_full_story"))
							message.setFullStoryLink(save_file("msg_full_story", "/../../nlesdweb/WebContent/announcements/doc/"));

						message.setImageCaption(form.get("msg_img_caption"));

						message.setShowOnFrontPage(form.exists("msg_front_page"));

						message.setArchived(form.exists("msg_archived"));

						if (form.exists("school_id") && (form.getInt("school_id") > -1))
							message.setSchool(SchoolDB.getSchool(form.getInt("school_id")));
						else
							message.setSchool(null);

						AnnouncementManager.updateAnnouncementBean(message);

						request.setAttribute("edit_msg", "MESSAGE UPDATED SUCCESSFULLY");

						TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>> years = AnnouncementManager.getArchivedAnnouncementBeansMap(
								message.getType(), message.getSchool());

						Calendar cal = Calendar.getInstance();
						cal.setTime(message.getDate());

						TreeMap<String, Vector<AnnouncementBean>> year = years.get(Utils.getSchoolYear(cal));

						request.setAttribute("YEARS", years.keySet());
						request.setAttribute("YEAR", Utils.getSchoolYear(cal));
						request.setAttribute("MONTHS", year.keySet());
						request.setAttribute("MONTH", DateUtils.getMonthString(cal.get(Calendar.MONTH)));

						request.setAttribute("MESSAGES", year.get(DateUtils.getMonthString(cal.get(Calendar.MONTH))));

						request.setAttribute("VIEW_TYPE", new Integer(message.getType().getTypeID()));
						request.setAttribute("VIEW_SCHOOL", message.getSchool());

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
						new RequiredFormElement("id")
				});

				if (validate_form()) {
					try {
						AnnouncementBean message = AnnouncementManager.getAnnouncementBean(form.getInt("id"));

						TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>> years = AnnouncementManager.getArchivedAnnouncementBeansMap(
								message.getType(), message.getSchool());

						Calendar cal = Calendar.getInstance();
						cal.setTime(message.getDate());

						TreeMap<String, Vector<AnnouncementBean>> year = years.get(Utils.getSchoolYear(cal));

						request.setAttribute("message", message);

						request.setAttribute("YEARS", years.keySet());
						request.setAttribute("YEAR", Utils.getSchoolYear(cal));
						request.setAttribute("MONTHS", year.keySet());
						request.setAttribute("MONTH", DateUtils.getMonthString(cal.get(Calendar.MONTH)));

						request.setAttribute("MESSAGES", year.get(DateUtils.getMonthString(cal.get(Calendar.MONTH))));

						request.setAttribute("VIEW_TYPE", new Integer(message.getType().getTypeID()));
						request.setAttribute("VIEW_SCHOOL", message.getSchool());
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

		path = "archived_announcements.jsp";

		return path;
	}
}