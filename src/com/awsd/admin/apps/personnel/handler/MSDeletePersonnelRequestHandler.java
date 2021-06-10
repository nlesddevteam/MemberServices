package com.awsd.admin.apps.personnel.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class MSDeletePersonnelRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
   // throws ServletException, IOException
  {	  
	  String path;
	
	  try {  
   
    HttpSession session = null;
    User usr = null;
   
    int pid;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getUsername() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("pid") == null)
    {
      throw new AdminException("Personnel ID Required.");
    }
    else
    {
      pid = Integer.parseInt(request.getParameter("pid"));
    }

    PersonnelDB.deletePersonnel(pid);
    //session.setAttribute("ADMIN-PERSONNEL_VIEW", null);
    request.setAttribute("msgOK", "<b>SUCCESS</b>: User has been removed!");   
 
  } catch (Exception e) {	
	  request.setAttribute("msgERR", "<b>ERROR</b>: User cannot be removed. Possible user data such as TravelClaim, PD or PLP information exists. Check for any in use data before removal of this user. ");    
	  
  } 	  
	  path = "personnel_admin_view.jsp";    
	    return path;
	  
  }  
}