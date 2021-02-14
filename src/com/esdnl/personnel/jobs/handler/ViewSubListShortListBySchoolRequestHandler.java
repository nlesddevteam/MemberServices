package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.school.service.SchoolZoneService;
public class ViewSubListShortListBySchoolRequestHandler extends RequestHandlerImpl {
	public ViewSubListShortListBySchoolRequestHandler() {
		
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		try {
				if (!usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")) {
					throw new SecurityException("Ilegal access attempt sublist shortlist.");
				}
				request.setAttribute("zones", SchoolZoneService.getSchoolZoneBeans());
				path = "admin_view_sub_list_by_school.jsp";
				        
				        
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_sub_list_by_school.jsp";
		}

		return path;
	}
}