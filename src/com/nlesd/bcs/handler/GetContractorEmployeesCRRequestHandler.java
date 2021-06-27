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

public class GetContractorEmployeesCRRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public GetContractorEmployeesCRRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			super.handleRequest(request, response);
			ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
			BussingContractorBean cbean = null;
			cbean = BussingContractorManager.getBussingContractorById(bcbean.getId());
			list = BussingContractorEmployeeManager.getContractorsEmployees(bcbean.getId());
			request.setAttribute("employees", list);
			String cname = "";
			if(cbean.getCompany() != null){
				cname=cbean.getLastName() + ", " + cbean.getFirstName() + "(" + cbean.getCompany() + ")";
			}else{
				cname=cbean.getLastName() + ", " + cbean.getFirstName();
			}
			request.setAttribute("companyname",cname);
			path = "view_employee_details_con.jsp";
			        
		
		
			
		return path;
	}

}
