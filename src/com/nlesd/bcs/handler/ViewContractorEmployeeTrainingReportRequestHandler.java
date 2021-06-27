package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorManager;

public class ViewContractorEmployeeTrainingReportRequestHandler extends RequestHandlerImpl
{
	public ViewContractorEmployeeTrainingReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("contractors", BussingContractorManager.getAllContractors());
		path = "view_contractor_employee_training_report.jsp";
		return path;
	}
}
