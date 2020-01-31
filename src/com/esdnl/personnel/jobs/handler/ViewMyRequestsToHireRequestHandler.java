package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewMyRequestsToHireRequestHandler extends RequestHandlerImpl {
	public ViewMyRequestsToHireRequestHandler() {
		requiredPermissions = new String[] {
				"PERSONNEL-ADREQUEST-REQUEST","RTH-NEW-REQUEST"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		RequestToHireBean[] list = null;

		try {
			list = RequestToHireManager.getMyRequestsToHireSubmitted(usr.getPersonnel().getPersonnelID());
			request.setAttribute("requests", list);
			path = "admin_view_my_requests_to_hire.jsp";
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_view_my_requests_to_hire.jsp";
		}

		return path;
	}
}