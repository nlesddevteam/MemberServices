package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.MyHrpSettingsBean;
import com.esdnl.personnel.jobs.dao.MyHrpSettingsManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewMyHrpSettingsRequestHandler  extends RequestHandlerImpl {

	public ViewMyHrpSettingsRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);
		MyHrpSettingsBean rbean = MyHrpSettingsManager.getMyHrpSettings();
		request.setAttribute("settings", rbean);
		path = "admin_myhrp_settings.jsp";
		return path;
	}
}