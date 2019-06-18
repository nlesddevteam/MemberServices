package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsException;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.constants.NewsStatus;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

public class ViewNewsPostingsDetailsRequestHandler extends RequestHandlerImpl {
	public ViewNewsPostingsDetailsRequestHandler() {

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
			Map<Integer,String> categorylist = new HashMap<Integer,String>();
			for(NewsCategory t : NewsCategory.ALL)
			{
				categorylist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			Map<Integer,String> statuslist = new HashMap<Integer,String>();
			for(NewsStatus t : NewsStatus.ALL)
			{
				statuslist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			
			LocationBean[] listregions = LocationManager.getLocationBeans();
			request.setAttribute("locationlist", listregions);
			request.setAttribute("newspostings", NewsPostingsManager.getNewsPostingsById(Integer.parseInt(request.getParameter("id").toString())));
			path = "view_news_postings_details.jsp";
			
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NewsPostingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = "view_news_postings_details.jsp";;
	    return path;
	}
}
