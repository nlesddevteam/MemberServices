package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;
import com.esdnl.webupdatesystem.blogs.dao.BlogFileManager;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;

public class DeleteBlogOtherFileRequestHandler extends RequestHandlerImpl {

	public DeleteBlogOtherFileRequestHandler() {

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
			Integer blogid = Integer.parseInt(form.get("bid"));
			//get list of files to delete from server directory
			String filelocation = "/../../nlesdweb/WebContent/includes/files/blogs/doc/";
			delete_file(filelocation, fid);
			BlogFileManager.deleteBlogFile(id);
			Map<Integer, String> statuslist = new HashMap<Integer, String>();
			for (BlogStatus t : BlogStatus.ALL) {
				statuslist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			request.setAttribute("blog", BlogsManager.getBlogById(blogid));
			request.setAttribute("msg", "Other Blog File has been deleted");
			path = "view_blog_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_blog_details.jsp";
		}
		return path;
	}
}
