package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
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
			int cid=0;
			if(usr.checkPermission("BCS-VIEW-WESTERN")){
				cid = BoardOwnedContractorsConstant.WESTERN.getValue();
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
			}
		}else{
			request.setAttribute("contracts", BussingContractorSystemContractManager.getContracts());
		}

		path = "admin_view_contracts.jsp";

		return path;
	}
}
