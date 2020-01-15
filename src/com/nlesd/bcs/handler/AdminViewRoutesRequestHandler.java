package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AdminViewRoutesRequestHandler  extends RequestHandlerImpl
{
	public AdminViewRoutesRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}	
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
			int cid=0;
			if(usr.checkPermission("BCS-VIEW-WESTERN")){
				cid = BoardOwnedContractorsConstant.WESTERN.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdminList(cid));
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdminList(cid));
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdminList(cid));
			}
		}else{
			request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesList());
		}

		path = "admin_view_routes.jsp";


		return path;
	}
}
