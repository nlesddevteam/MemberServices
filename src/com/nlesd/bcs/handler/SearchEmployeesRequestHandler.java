package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;
public class SearchEmployeesRequestHandler extends RequestHandlerImpl
{
	public SearchEmployeesRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		TreeMap<Integer,String> status =new TreeMap<Integer,String>();
		for(EmployeeStatusConstant sc : EmployeeStatusConstant.ALL)
		{
			status.put(sc.getValue(),sc.getDescription());
		}
		request.setAttribute("status", status);
		//get search by values
		request.setAttribute("sby", DropdownManager.getDropdownValuesLinkedHM(9));
		//get employee position values
		request.setAttribute("epositions", DropdownManager.getDropdownValuesLinkedHM(6));
		//get dl class values
		request.setAttribute("dlclasses", DropdownManager.getDropdownValuesLinkedHM(7));
		//now we add the regional/depot dropdowns
		request.setAttribute("rcodes", DropdownManager.getDropdownValuesTM(24));
		request.setAttribute("dcodes", DropdownManager.getDropdownValuesTM(25));
		path = "search_employees.jsp";


		return path;
	}
}
