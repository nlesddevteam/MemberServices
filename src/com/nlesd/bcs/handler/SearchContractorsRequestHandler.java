package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.DropdownManager;

public class SearchContractorsRequestHandler implements RequestHandler
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