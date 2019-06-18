package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;
public class SearchEmployeesRequestHandler implements RequestHandler
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
