package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;

public class ViewEmployeeTrainingRequestHandler extends RequestHandlerImpl
{
	public ViewEmployeeTrainingRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("ttypes", DropdownManager.getDropdownValuesLinkedHM(22));
		path = "view_employee_training_report.jsp";
		return path;
	}
}
