package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewSchoolOutstandingRequestsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String username = "";
    String hash = "";
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if(usr.getPersonnel().getSchool() != null)
    	session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingMaintenanceRequests(usr.getPersonnel().getSchool()));
  
    path = "school_outstanding_requests.jsp";
    
    return path;
  }
}