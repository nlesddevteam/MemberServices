package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.DropdownManager;

public class SearchContractorsRequestHandler extends RequestHandlerImpl
{
	public SearchContractorsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		TreeMap<Integer,String> status =new TreeMap<Integer,String>();
		for(StatusConstant sc : StatusConstant.ALL)
		{
			status.put(sc.getValue(),sc.getDescription());
		}
		request.setAttribute("status", status);
		TreeMap<Integer,String> items;
		items = DropdownManager.getDropdownValuesTM(8);
		request.setAttribute("sby", items);

		path = "search_contractors.jsp";



		return path;
	}
}