package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsFileManager;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

public class DeleteNewsPostingsOtherFileRequestHandler extends RequestHandlerImpl {

	public DeleteNewsPostingsOtherFileRequestHandler() {

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
			Integer npid = Integer.parseInt(form.get("npid"));
			//get list of files to delete from server directory
			String filelocation = "/../../nlesdweb/WebContent/includes/files/news/doc/";
			delete_file(filelocation, fid);
			NewsPostingsFileManager.deleteNewsPostingsFile(id);
			request.setAttribute("newspostings", NewsPostingsManager.getNewsPostingsById(npid));
			Map<Integer, String> categorylist = new HashMap<Integer, String>();
			for (MeetingCategory t : MeetingCategory.ALL) {
				categorylist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			request.setAttribute("msg", "Other News Postings File has been deleted");
			path = "view_news_postings_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_news_postings_details.jsp";
		}
		return path;
	}
}
