package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
import com.nlesd.bcs.dao.DropdownManager;
import com.nlesd.bcs.handler.BCSApplicationRequestHandlerImpl;
public class ViewContractInfoRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewContractInfoRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
		if(ebean == null)
	      {
	        path = "login.jsp";
	      }else{
	    	  int cid = form.getInt("cid");
	    	  BussingContractorSystemContractBean bcbean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(cid);
	    	request.setAttribute("contractor",ebean);
	    	request.setAttribute("contract",bcbean);
	    	request.setAttribute("vehicletype", DropdownManager.getDropdownItemText(bcbean.getVehicleType()));
	    	request.setAttribute("vehiclesize", DropdownManager.getDropdownItemText(bcbean.getVehicleSize()));
		    ArrayList<BussingContractorSystemRouteBean> alist = BussingContractorSystemRouteManager.getBussingContractorSystemRouteByContactId(cid);
		    request.setAttribute("croutes",alist);
		  
	  		path = "view_contract_info.jsp";
	      }
		
		
		return path;
	}
}