package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.MyHrpSettingsBean;
import com.esdnl.personnel.jobs.dao.MyHrpSettingsManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class UpdateMyHrpSettingsRequestHandler extends RequestHandlerImpl {

	public UpdateMyHrpSettingsRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		MyHrpSettingsBean rbean = new MyHrpSettingsBean();
		if(form.exists("blockschools")) {
			rbean.setPpBlockSchools(true);
		}else {
			rbean.setPpBlockSchools(false);
		}
		MyHrpSettingsManager.updateMyHrpSettings(rbean);
		request.setAttribute("settings", rbean);
		request.setAttribute("msgok", "Settings have been updated.");
		path = "admin_myhrp_settings.jsp";
		return path;
	}
}
