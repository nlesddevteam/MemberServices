package com.nlesd.bcs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.RouteRunDriverVehicleManager;

public class ViewRouteRunDriverReportRequestHandler  extends RequestHandlerImpl
{
	public ViewRouteRunDriverReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("entries", RouteRunDriverVehicleManager.getRouteRunDriverVehicleData());
		path = "admin_view_route_run_details.jsp";
		return path;
	}
}
