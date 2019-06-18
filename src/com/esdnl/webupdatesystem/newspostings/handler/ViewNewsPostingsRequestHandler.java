package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsException;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

public class ViewNewsPostingsRequestHandler extends RequestHandlerImpl {
	public ViewNewsPostingsRequestHandler() {
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
			//request.setAttribute("newspostings", NewsPostingsManager.getNewsPostings());
	    	request.setAttribute("newspostings", NewsPostingsManager.getNewsPostingsByCat(1, 6, 1));
			
		} catch (NewsPostingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_news_postings.jsp";
	    return path;
	}
}
