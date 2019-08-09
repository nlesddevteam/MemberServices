package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsBean;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

public class DeleteNewsPostingsRequestHandler extends RequestHandlerImpl {

	public DeleteNewsPostingsRequestHandler() {

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
			Integer fileId = Integer.parseInt(request.getParameter("pid").toString());
			NewsPostingsBean npb = NewsPostingsManager.getNewsPostingsById(fileId);
			String filelocation = "/../../nlesdweb/WebContent/includes/files/news/img/";
			if (!(npb.getNewsPhoto() == null)) {
				delete_file(filelocation, npb.getNewsPhoto());
			}
			filelocation = "/../../nlesdweb/WebContent/includes/files/news/doc/";
			if (!(npb.getNewsDocumentation() == null)) {
				delete_file(filelocation, npb.getNewsDocumentation());
			}
			NewsPostingsManager.deleteNewsPostings(fileId);
			request.setAttribute("msg", "News Postings has been deleted");

			path = "viewNewsPostings.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewNewsPostings.html";
		}
		return path;
	}
}