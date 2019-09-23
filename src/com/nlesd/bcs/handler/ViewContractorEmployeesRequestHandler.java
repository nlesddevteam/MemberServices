package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
public class ViewContractorEmployeesRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewContractorEmployeesRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
		ArrayList<BussingContractorEmployeeBean> employees = new ArrayList<BussingContractorEmployeeBean>();
		int status=0;
		if(!(request.getParameter("status") == null)){
			status = Integer.parseInt(request.getParameter("status"));
		}

		if(status > 0){
			employees = BussingContractorEmployeeManager.getContractorsEmployeesByStatus(bcbean.getId(),status);
		}else{
			employees = BussingContractorEmployeeManager.getContractorsEmployees(bcbean.getId());
		}
		request.setAttribute("employees", employees);
		path = "view_contractor_employees.jsp";


		return path;
	}
}
