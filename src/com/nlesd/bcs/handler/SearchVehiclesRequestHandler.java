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
		//get search by values
		request.setAttribute("sby", DropdownManager.getDropdownValuesLinkedHM(10));
		//get vehicle makes
		request.setAttribute("makes", DropdownManager.getDropdownValuesLinkedHM(1));
		//get vehicle models
		request.setAttribute("models", DropdownManager.getDropdownValuesLinkedHM(2));
		//get vehicle types
		request.setAttribute("types", DropdownManager.getDropdownValuesLinkedHM(3));
		//get vehicle sizes
		request.setAttribute("sizes", DropdownManager.getDropdownValuesLinkedHM(4));
		//now we add the regional/depot dropdowns
		request.setAttribute("rcodes", DropdownManager.getDropdownValuesLinkedHM(24));
		request.setAttribute("dcodes", DropdownManager.getDropdownValuesLinkedHM(25));
					
		path = "search_vehicles.jsp";


		return path;
	}
}
