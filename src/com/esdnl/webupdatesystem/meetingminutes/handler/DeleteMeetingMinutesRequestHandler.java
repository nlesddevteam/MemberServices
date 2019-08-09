package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesBean;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;

public class DeleteMeetingMinutesRequestHandler extends RequestHandlerImpl {

	public DeleteMeetingMinutesRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		try {
			Integer fileId = Integer.parseInt(request.getParameter("mmid").toString());

			MeetingMinutesBean mmb = MeetingMinutesManager.getMeetingMinutesById(fileId);
			String filelocation = "/../../nlesdweb/WebContent/includes/files/minutes/doc/";
			delete_file(filelocation, mmb.getmMDoc());
			if (!(mmb.getmMRelDoc() == null)) {
				delete_file(filelocation, mmb.getmMRelDoc());
			}
			if (!(mmb.getmMRelPreDoc() == null)) {
				delete_file(filelocation, mmb.getmMRelPreDoc());
			}
			MeetingMinutesManager.deleteMeetingMinutes(mmb.getId());

			request.setAttribute("msg", "Meeting Minutes has been deleted");

			path = "viewMeetingMinutes.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewMeetingMinutes.html";
		}
		return path;
	}
}
