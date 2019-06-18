package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;
public class SearchVehiclesRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
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
