package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class ViewContractorVehiclesRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewContractorVehiclesRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
		ArrayList<BussingContractorVehicleBean> vehicles = new ArrayList<BussingContractorVehicleBean>();
		int status=0;
		if(!(request.getParameter("status")== null)){
			status = Integer.parseInt(request.getParameter("status"));
		}

		if(status > 0){
			vehicles = BussingContractorVehicleManager.getContractorsVehiclesByStatus(bcbean.getId(),status);
		}else{
			vehicles = BussingContractorVehicleManager.getContractorsVehicles(bcbean.getId());
		}
		request.setAttribute("vehicles", vehicles);
		path = "view_contractor_vehicles.jsp";


		return path;
	}
}