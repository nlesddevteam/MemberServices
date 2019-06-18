package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ViewBlogRequestHandler extends RequestHandlerImpl {
	public ViewBlogRequestHandler() {

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
	    	request.setAttribute("blog", BlogsManager.getBlogById(Integer.parseInt(request.getParameter("id").toString())));
	    	Map<Integer,String> statuslist = new HashMap<Integer,String>();
			for(BlogStatus t : BlogStatus.ALL)
			{
				statuslist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			
		} catch (BlogsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_blog_details.jsp";
	    return path;
	}
}