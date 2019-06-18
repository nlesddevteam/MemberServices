package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;

public class ViewBlogsRequestHandler extends RequestHandlerImpl {
	public ViewBlogsRequestHandler() {
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
			request.setAttribute("blogs", BlogsManager.getBlogs());
		} catch (BlogsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_blogs.jsp";
	    return path;
	}
}
