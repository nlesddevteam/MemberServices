package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.banners.bean.BannersBean;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;
import com.esdnl.webupdatesystem.blogs.bean.BlogsBean;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;

public class DeleteBlogRequestHandler  extends RequestHandlerImpl {
	public DeleteBlogRequestHandler() {
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
			Integer fileId=Integer.parseInt(request.getParameter("bid").toString());
			BlogsBean bb= BlogsManager.getBlogById(fileId);
			String filelocation="/../ROOT/includes/files/blog/doc/";
			if(!(bb.getBlogDocument() == null))
			{
				delete_file(filelocation, bb.getBlogDocument());
			}
			filelocation="/../ROOT/includes/files/blog/img/";
			if(!(bb.getBlogPhoto() == null))
			{
				delete_file(filelocation, bb.getBlogPhoto());
			}
			BlogsManager.deleteBlog(bb.getId());
			
			request.setAttribute("msg", "Blog has been deleted");
			
			path = "viewBlogs.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewBlogs.html";
		}
		return path;
	}
}

