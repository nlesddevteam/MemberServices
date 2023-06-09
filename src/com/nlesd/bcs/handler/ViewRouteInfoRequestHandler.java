package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.handler.BCSApplicationRequestHandlerImpl;
public class ViewRouteInfoRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewRouteInfoRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("rid")
			});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
	    	  int cid = form.getInt("cid");
	    	  int rid = form.getInt("rid");
	    	  BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
	    	  BussingContractorSystemContractBean bcbean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(cid);
	    	  BussingContractorSystemRouteBean rbean = BussingContractorSystemRouteManager.getBussingContractorSystemRouteById(rid);
	    	  request.setAttribute("contractor",ebean);
	    	  request.setAttribute("contract",bcbean);
	    	  request.setAttribute("route",rbean);
	    	  BussingContractorEmployeeBean origbean = BussingContractorEmployeeManager.getCurrentRouteDriver(rid);
	    	  if(origbean.getFirstName() == null){
	    		  request.setAttribute("assigneddriver", "None Assigned");
		    	  request.setAttribute("assigneddriverid", "-1");
	    	  }else{
	    		  request.setAttribute("assigneddriver", origbean.getLastName() + "," + origbean.getFirstName());
		    	  request.setAttribute("assigneddriverid", origbean.getId());
	    	  }
	    	  BussingContractorVehicleBean vorigbean = BussingContractorVehicleManager.getCurrentRouteVehicle(rid);
	    	  if(vorigbean.getvPlateNumber() == null){
	    		  request.setAttribute("assignedvehicle", "None Assigned");
		    	  request.setAttribute("assignedvehicleid", "-1");
	    	  }else{
	    		  request.setAttribute("assignedvehicle", vorigbean.getvPlateNumber() + "[" + vorigbean.getvSerialNumber() + "]");
		    	  request.setAttribute("assignedvehicleid", vorigbean.getId());
	    	  }
	    	  
	    	  request.setAttribute("drivers", BussingContractorEmployeeManager.getApprovedContractorsDrivers(ebean.getId()));
	    	  request.setAttribute("vehicles", BussingContractorVehicleManager.getApprovedContractorsVehicles(ebean.getId()));
	    	  path = "view_route_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;
		}
		return path;
	}
}
