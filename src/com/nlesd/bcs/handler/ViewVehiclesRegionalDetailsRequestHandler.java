package com.nlesd.bcs.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;

public class ViewVehiclesRegionalDetailsRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewVehiclesRegionalDetailsRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			super.handleRequest(request, response);
			ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
			BussingContractorBean cbean = null;
			cbean = BussingContractorManager.getBussingContractorById(bcbean.getId());
			int regionid = form.getInt("regionid");
			int depotid = form.getInt("depotid");
			if(depotid == -1 || depotid == 0) {
				list=BussingContractorVehicleManager.searchVehiclesByRegion(regionid);
			}else {
				list=BussingContractorVehicleManager.getVehiclesByRegionDepot(regionid,depotid);
			}
			
			request.setAttribute("vehicles", list);
			String cname = "";
			if(cbean.getCompany() != null){
				cname=cbean.getLastName() + ", " + cbean.getFirstName() + "(" + cbean.getCompany() + ")";
			}else{
				cname=cbean.getLastName() + ", " + cbean.getFirstName();
			}
			request.setAttribute("companyname",cname);
			path = "view_vehicles_by_region.jsp";
			        
		
		
			
		return path;
	}
}