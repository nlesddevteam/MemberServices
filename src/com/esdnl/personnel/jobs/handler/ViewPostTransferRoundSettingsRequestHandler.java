package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.PostTransferRoundSettingsBean;
import com.esdnl.personnel.jobs.dao.PostTransferRoundSettingsManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewPostTransferRoundSettingsRequestHandler extends RequestHandlerImpl {

	public ViewPostTransferRoundSettingsRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);
		PostTransferRoundSettingsBean rbean = PostTransferRoundSettingsManager.getPostTransferRoundSettings();
		request.setAttribute("settings", rbean);
		path = "admin_post_transfer_round.jsp";
		return path;
	}
}