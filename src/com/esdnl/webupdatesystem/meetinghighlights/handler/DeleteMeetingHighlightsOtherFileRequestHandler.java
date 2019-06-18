package com.esdnl.webupdatesystem.meetinghighlights.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsFileManager;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsManager;
public class DeleteMeetingHighlightsOtherFileRequestHandler extends RequestHandlerImpl {
	public DeleteMeetingHighlightsOtherFileRequestHandler() {
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
			String fid=form.get("fid");
			Integer id =Integer.parseInt(form.get("id"));
			Integer mhid =Integer.parseInt(form.get("mhid"));
			//get list of files to delete from server directory
			String filelocation="/../ROOT/includes/files/highlights/doc/";
			delete_file(filelocation, fid);
			MeetingHighlightsFileManager.deleteMeetingHighlightsFile(id);
	    	request.setAttribute("meetinghighlights", MeetingHighlightsManager.getMeetingHighlightsById(mhid));
	    	request.setAttribute("msg", "Other Meeting Highlights File has been deleted");
			path = "view_meeting_highlights_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_meeting_highlights_details.jsp";
		}
		return path;
	}
}