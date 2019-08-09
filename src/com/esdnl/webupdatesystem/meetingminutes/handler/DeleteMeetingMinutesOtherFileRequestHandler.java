package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesFileManager;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.constants.NewsStatus;

public class DeleteMeetingMinutesOtherFileRequestHandler extends RequestHandlerImpl {

	public DeleteMeetingMinutesOtherFileRequestHandler() {

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
			String fid = form.get("fid");
			Integer id = Integer.parseInt(form.get("id"));
			Integer mmid = Integer.parseInt(form.get("mmid"));
			//get list of files to delete from server directory
			String filelocation = "/../../nlesdweb/WebContent/includes/files/minutes/doc/";
			delete_file(filelocation, fid);
			MeetingMinutesFileManager.deleteMeetingMinutesFile(id);
			request.setAttribute("meetingminutes", MeetingMinutesManager.getMeetingMinutesById(mmid));
			Map<Integer, String> categorylist = new HashMap<Integer, String>();
			for (NewsCategory t : NewsCategory.ALL) {
				categorylist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			Map<Integer, String> statuslist = new HashMap<Integer, String>();
			for (NewsStatus t : NewsStatus.ALL) {
				statuslist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);

			LocationBean[] listregions = LocationManager.getLocationBeans();
			request.setAttribute("locationlist", listregions);
			request.setAttribute("msg", "Other Meeting Minutes File has been deleted");
			path = "view_meeting_minutes_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_meeting_minutes_details.jsp";
		}
		return path;
	}
}