package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
public class ViewMyReportsRequestHandler extends RequestHandlerImpl
{
	public ViewMyReportsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		request.setAttribute("reports", BussingContractorSystemCustomReportManager.getCustomReportsByUser(usr.getPersonnel().getFullNameReverse()));
		path = "view_my_reports.jsp";


		return path;
	}
}