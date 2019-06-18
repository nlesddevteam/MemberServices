package com.esdnl.webupdatesystem.meetinghighlights.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsException;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsManager;
public class ViewMeetingHighlightRequestHandler extends RequestHandlerImpl {
	public ViewMeetingHighlightRequestHandler() {

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
	    	request.setAttribute("meetinghighlights", MeetingHighlightsManager.getMeetingHighlightsById(Integer.parseInt(request.getParameter("id").toString())));

			
		} catch (MeetingHighlightsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_meeting_highlights_details.jsp";
	    return path;
	}
}

