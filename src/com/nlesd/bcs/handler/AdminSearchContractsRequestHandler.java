package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminSearchContractsRequestHandler extends RequestHandlerImpl
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
	    TreeMap<Integer,String> items;
		items = DropdownManager.getDropdownValuesTM(15);
		//remove option to search by region if regional admin
		if(usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN") || usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL") || usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR")){
			items.remove(77);
		}
		request.setAttribute("sby", items);
		items = DropdownManager.getDropdownValuesTM(13);
		request.setAttribute("regions", items);
		items = DropdownManager.getDropdownValuesTM(14);
		request.setAttribute("types", items);
		
	    path = "admin_search_contracts.jsp";
	    return path;
	  }
}