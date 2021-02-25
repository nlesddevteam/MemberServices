package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;

public class ViewEmployeesMissingSPTReportRequestHandler extends RequestHandlerImpl
{
	public ViewEmployeesMissingSPTReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("employees", BussingContractorEmployeeManager.getEmployeeMissingSPT(Utils.getCurrentSchoolYear()));
		path = "view_employee_missing_spt.jsp";
		return path;
	}
}