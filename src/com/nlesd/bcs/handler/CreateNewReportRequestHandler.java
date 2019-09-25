package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableBean;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableManager;
public class CreateNewReportRequestHandler extends RequestHandlerImpl
{
	public CreateNewReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		ArrayList<BussingContractorSystemReportTableBean> list = BussingContractorSystemReportTableManager.getReportTables();
		//now we remove the contractor table if regional admin
		if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
			BussingContractorSystemReportTableBean fbean = BussingContractorSystemReportTableManager.getReportTableById(1);
			list.remove(fbean);
			fbean = BussingContractorSystemReportTableManager.getReportTableById(5);
			list.remove(fbean);
			fbean = BussingContractorSystemReportTableManager.getReportTableById(6);
			list.remove(fbean);
		}
		request.setAttribute("tables",list );
		path = "admin_create_new_report.jsp";


		return path;
	}
}
