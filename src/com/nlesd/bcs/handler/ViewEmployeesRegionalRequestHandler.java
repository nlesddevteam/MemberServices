package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.dao.DropdownManager;

public class ViewEmployeesRegionalRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewEmployeesRegionalRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			super.handleRequest(request, response);
			  request.setAttribute("regions", DropdownManager.getDropdownValuesTM(24));
			  request.setAttribute("depots", DropdownManager.getDropdownValuesTM(25));
			path = "view_employees_regional.jsp";
			        
		
		
			
		return path;
	}

}
