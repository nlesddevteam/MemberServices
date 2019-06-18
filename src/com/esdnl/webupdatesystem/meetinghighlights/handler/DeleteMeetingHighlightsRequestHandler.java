package com.esdnl.webupdatesystem.meetinghighlights.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsBean;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsManager;
public class DeleteMeetingHighlightsRequestHandler extends RequestHandlerImpl {
	public DeleteMeetingHighlightsRequestHandler() {
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
			Integer fileId=Integer.parseInt(request.getParameter("mhid").toString());
			
			MeetingHighlightsBean mmb = MeetingHighlightsManager.getMeetingHighlightsById(fileId);
			String filelocation="/../ROOT/includes/files/highlights/doc/";
			delete_file(filelocation, mmb.getmHDoc());
			if(!(mmb.getmHRelDoc()== null) )
			{
				delete_file(filelocation, mmb.getmHRelDoc());
			}
			if(!(mmb.getmHRelPreDoc() == null) )
			{
				delete_file(filelocation, mmb.getmHRelPreDoc());
			}
			MeetingHighlightsManager.deleteMeetingHighlights(mmb.getId());
			
			
			request.setAttribute("msg", "Meeting Highlights has been deleted");
			
			path = "viewMeetingHighlights.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewMeetingHighlights.html";
		}
		return path;
	}
}
