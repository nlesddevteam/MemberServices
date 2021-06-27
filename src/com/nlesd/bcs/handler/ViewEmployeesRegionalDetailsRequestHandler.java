package com.nlesd.bcs.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;

public class ViewEmployeesRegionalDetailsRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewEmployeesRegionalDetailsRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			super.handleRequest(request, response);
			ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
			BussingContractorBean cbean = null;
			cbean = BussingContractorManager.getBussingContractorById(bcbean.getId());
			int regionid = form.getInt("regionid");
			int depotid = form.getInt("depotid");
			if(depotid == -1 || depotid == 0) {
				list=BussingContractorEmployeeManager.getContractorsEmployeesByRegion(regionid);
			}else {
				list=BussingContractorEmployeeManager.getContractorsEmployeesByRegionDepot(regionid,depotid);
			}
			
			request.setAttribute("employees", list);
			String cname = "";
			if(cbean.getCompany() != null){
				cname=cbean.getLastName() + ", " + cbean.getFirstName() + "(" + cbean.getCompany() + ")";
			}else{
				cname=cbean.getLastName() + ", " + cbean.getFirstName();
			}
			request.setAttribute("companyname",cname);
			path = "view_employees_by_region.jsp";
			        
		
		
			
		return path;
	}

}
