package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;

public class ViewContractorEmployeeConvictionsRequestHandler extends RequestHandlerImpl
{
	public ViewContractorEmployeeConvictionsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("convicttypes", DropdownManager.getDropdownValuesLinkedHM(26));
		path = "view_employee_convictions_report.jsp";
		return path;
	}
}