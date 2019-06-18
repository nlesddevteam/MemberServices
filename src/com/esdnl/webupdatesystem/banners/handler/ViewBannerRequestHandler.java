package com.esdnl.webupdatesystem.banners.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.banners.bean.BannersException;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;
public class ViewBannerRequestHandler extends RequestHandlerImpl {
	public ViewBannerRequestHandler() {

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
	    	request.setAttribute("banner", BannersManager.getBannerById(Integer.parseInt(request.getParameter("id").toString())));

			
		} catch (BannersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_banner_details.jsp";
	    return path;
	}
}
