package com.esdnl.webupdatesystem.banners.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.banners.bean.BannersBean;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;
public class DeleteBannerRequestHandler extends RequestHandlerImpl {
	public DeleteBannerRequestHandler() {
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
			BannersBean bb= BannersManager.getBannerById(fileId);
			String filelocation="/../ROOT/includes/files/banners/img/";
			delete_file(filelocation, bb.getBannerFile());
			BannersManager.deleteBanner(bb.getId());
			
			request.setAttribute("msg", "Banner has been deleted");
			
			path = "viewBanners.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewBanners.html";
		}
		return path;
	}
}
