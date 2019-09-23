package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;
public class SearchVehiclesRequestHandler extends RequestHandlerImpl
{
	public SearchVehiclesRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		TreeMap<Integer,String> status =new TreeMap<Integer,String>();
		for(VehicleStatusConstant sc : VehicleStatusConstant.ALL)
		{
			status.put(sc.getValue(),sc.getDescription());
		}
		request.setAttribute("status", status);
		TreeMap<Integer,String> items;
		//get search by values
		items = DropdownManager.getDropdownValuesTM(10);
		request.setAttribute("sby", items);
		//get vehicle makes
		items = DropdownManager.getDropdownValuesTM(1);
		request.setAttribute("makes", items);
		//get vehicle models
		items = DropdownManager.getDropdownValuesTM(2);
		request.setAttribute("models", items);
		//get vehicle types
		items = DropdownManager.getDropdownValuesTM(3);
		request.setAttribute("types", items);
		//get vehicle sizes
		items = DropdownManager.getDropdownValuesTM(4);
		request.setAttribute("sizes", items);			
		path = "search_vehicles.jsp";


		return path;
	}
}
