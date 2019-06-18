package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesException;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;
public class ViewMeetingMinutesRequestHandler extends RequestHandlerImpl {
	public ViewMeetingMinutesRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("mms", MeetingMinutesManager.getMeetingMinutes());
		} catch (MeetingMinutesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_meeting_minutes.jsp";
	    return path;
	}
}