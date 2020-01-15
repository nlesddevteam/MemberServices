package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class AdminViewContractsRequestHandler extends RequestHandlerImpl
{
	public AdminViewContractsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}		
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
			if(usr.checkPermission("BCS-VIEW-WESTERN")){
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsFullByRegion(71));
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsFullByRegion(69));
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsFullByRegion(70));
			}
		}else{
			request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsFull());
		}

		path = "admin_view_contracts.jsp";

		return path;
	}
}
