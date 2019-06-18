package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

 
public class ViewWorkOrderOutstandingRequestsRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingWorkOrderMaintenanceRequestsPages(usr.getPersonnel(), 10));
  
    path = "workorder_outstanding_requests.jsp?page=1";
    
    return path;
  }
}