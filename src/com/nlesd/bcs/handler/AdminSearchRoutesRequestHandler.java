package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminSearchRoutesRequestHandler extends RequestHandlerImpl
{
	public AdminSearchRoutesRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		TreeMap<Integer,String> items;
		items = DropdownManager.getDropdownValuesTM(16);
		request.setAttribute("sby", items);
		TreeMap<String,Integer> items2;
		items2 = DropdownManager.getSchools();
		request.setAttribute("schools", items2);
		path = "admin_search_routes.jsp";


		return path;
	}
}
