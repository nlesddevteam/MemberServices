package com.esdnl.webmaint.minutes.handler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.webmaint.minutes.BoardMeetingMinutesDB;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadFile;

public class AddMinutesRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		UploadBean bean = null;
		MultipartFormDataRequest mrequest = null;
		UploadFile file = null;
		Hashtable files = null;
		File f = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
					&& usr.getUserPermissions().containsKey("WEBMAINTENANCE-BOARDMINUTES"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		try {
			if (MultipartFormDataRequest.isMultipartFormData(request)) {
				mrequest = new MultipartFormDataRequest(request);

				if (mrequest.getParameter("op") != null) {
					if (mrequest.getParameter("op").equals("CONFIRM")) {
						if (mrequest.getParameter("meeting_date") == null) {
							request.setAttribute("msg", "MEETING DATE IS REQUIRED");
						}
						else {
							files = mrequest.getFiles();

							if ((files == null) || (files.size() < 1)) {
								request.setAttribute("msg", "PLEASE SELECT MINUTES FILE TO UPLOAD");
							}
							else {
								file = (UploadFile) files.get("filedata");

								if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
									request.setAttribute("msg", "ONLY PDF POLICY FILES ARE ACCEPTED");
								}
								else {
									try {
										BoardMeetingMinutesDB.addMinutes(
												(new SimpleDateFormat("dd/MM/yyyy")).parse(mrequest.getParameter("meeting_date")));

										bean = new UploadBean();
										bean.setFolderstore(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/board/minutes/archive/esdnl/");
										bean.store(mrequest, "filedata");
										f = new File(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/board/minutes/archive/esdnl/" + file.getFileName());
										f.renameTo(new File(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/board/minutes/archive/esdnl/"
												+ mrequest.getParameter("meeting_date").replaceAll("/", "_") + ".pdf"));

										request.setAttribute("msg", "MINUTES ADD SUCCESSFULLY");
									}
									catch (SQLException e) {
										switch (e.getErrorCode()) {
										case 1:
											request.setAttribute("msg",
													"MINUTES FOR" + request.getParameter("meeting_date") + " ALREADY EXISTS");
											break;
										default:
											request.setAttribute("msg", e.getMessage());
										}
									}
								}
							}
						}
					}
					else
						request.setAttribute("msg", "INVALID OPTION");
				}
			}
		}
		catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
		}

		path = "add_minutes.jsp";

		return path;
	}
}