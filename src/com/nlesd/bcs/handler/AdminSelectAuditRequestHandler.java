package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AdminSelectAuditRequestHandler extends RequestHandlerImpl
{
	public AdminSelectAuditRequestHandler() {
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
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
			}
			ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
			list.add(BussingContractorManager.getBussingContractorById(cid));
			request.setAttribute("contractors",list );
		}else{

			TreeMap<String,BussingContractorBean> list = new TreeMap<String,BussingContractorBean>();
			list = BussingContractorManager.getAllContractorsTM();
			request.setAttribute("contractors", list);
		}

		path = "admin_select_audit.jsp";


		return path;
	}
}
