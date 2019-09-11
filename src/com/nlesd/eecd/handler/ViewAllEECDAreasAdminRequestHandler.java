package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
public class ViewAllEECDAreasAdminRequestHandler extends RequestHandlerImpl {
	public ViewAllEECDAreasAdminRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-SHORTLIST"
		};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		list = EECDAreaManager.getAllEECDAreas();
		request.setAttribute("areas", list);
		
			
		return "admin_view_eecd_areas.jsp";
	}

}