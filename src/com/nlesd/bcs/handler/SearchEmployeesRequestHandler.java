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
		TreeMap<Integer,String> items;
		//get search by values
		items = DropdownManager.getDropdownValuesTM(9);
		request.setAttribute("sby", items);
		//get employee position values
		items = DropdownManager.getDropdownValuesTM(6);
		request.setAttribute("epositions", items);
		//get dl class values
		items = DropdownManager.getDropdownValuesTM(7);
		request.setAttribute("dlclasses", items);			
		path = "search_employees.jsp";


		return path;
	}
}
