package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesException;
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;
public class ViewMeetingMinuteRequestHandler extends RequestHandlerImpl {
	public ViewMeetingMinuteRequestHandler() {

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
	    	request.setAttribute("meetingminutes", MeetingMinutesManager.getMeetingMinutesById(Integer.parseInt(request.getParameter("id").toString())));
			Map<Integer,String> categorylist = new HashMap<Integer,String>();
			for(MeetingCategory t : MeetingCategory.ALL)
			{
				categorylist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);

			
		} catch (MeetingMinutesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_meeting_minutes_details.jsp";
	    return path;
	}
}
