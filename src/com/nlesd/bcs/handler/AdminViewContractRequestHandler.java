package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemDocumentBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminViewContractRequestHandler extends RequestHandlerImpl
{
	public AdminViewContractRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}	
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		int docid;
		if(form.exists("vid")){
			docid = form.getInt("vid");
		}else{
			docid=-99;
		}

		BussingContractorSystemContractBean bcbean = new BussingContractorSystemContractBean();
		if(docid > 0){
			bcbean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(docid);
		}else{
			bcbean = new BussingContractorSystemContractBean();
		}

		TreeMap<Integer,String> items;
		//get search by values
		items = DropdownManager.getDropdownValuesTM(14);
		request.setAttribute("types", items);
		items = DropdownManager.getDropdownValuesTM(13);
		request.setAttribute("regions", items);
		request.setAttribute("contract",bcbean);
		TreeMap<Integer,String> items2;
		items2 = DropdownManager.getDropdownValuesTM(3);
		request.setAttribute("vtypes", items2);
		items2 = DropdownManager.getDropdownValuesTM(4);
		request.setAttribute("vsizes", items2);
		//get the documents related to contract
		ArrayList<BussingContractorSystemDocumentBean> list = BussingContractorSystemDocumentManager.getBussingContractorSystemContractDocuments(docid);
		request.setAttribute("documents",list);
		request.setAttribute("spath",request.getContextPath() + "/BCS/documents/system/contracts/");
		ArrayList<BussingContractorSystemRouteBean> alist = BussingContractorSystemRouteManager.getBussingContractorSystemRouteByContactId(docid);
		request.setAttribute("croutes",alist);
		alist = BussingContractorSystemRouteManager.getRoutes();
		request.setAttribute("allroutes",alist);

		//now we check to see if they are regional admin\
		if((usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN")|| usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL") || usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR"))){
			if((usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN"))){
				request.setAttribute("czone","Western");
				ArrayList<BussingContractorBean> listc = new ArrayList<BussingContractorBean>();
				listc.add(BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.WESTERN.getValue()));
				request.setAttribute("allcontractors",listc );
			}
			if((usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL"))){
				request.setAttribute("czone","Central");
				ArrayList<BussingContractorBean> listc = new ArrayList<BussingContractorBean>();
				listc.add(BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.CENTRAL.getValue()));
				request.setAttribute("allcontractors",listc );
			}
			if((usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR"))){
				request.setAttribute("czone","Labrador");
				ArrayList<BussingContractorBean> listc = new ArrayList<BussingContractorBean>();
				listc.add(BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.LABRADOR.getValue()));
				request.setAttribute("allcontractors",listc );
			}
		}else{
			ArrayList<BussingContractorBean> clist = BussingContractorManager.getApprovedContactors();
			request.setAttribute("allcontractors",clist);
		}
		path = "admin_view_contract.jsp";


		return path;
	}
}
