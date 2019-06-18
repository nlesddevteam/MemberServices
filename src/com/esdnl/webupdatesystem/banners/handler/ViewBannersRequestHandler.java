package com.esdnl.webupdatesystem.banners.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.banners.bean.BannersException;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;
public class ViewBannersRequestHandler  extends RequestHandlerImpl {
	public ViewBannersRequestHandler() {
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
			request.setAttribute("banners", BannersManager.getBanners());
		} catch (BannersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_banners.jsp";
	    return path;
	}
}