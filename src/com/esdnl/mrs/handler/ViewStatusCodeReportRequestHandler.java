package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewStatusCodeReportRequestHandler   implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    //if((usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW")))
    //  session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingRegionalMaintenanceRequestsPages(usr.getPersonnel(), 10));
    //else
      session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingMaintenanceRequestsPagesByStatus(usr.getPersonnel(), request.getParameter("code"), 10));
      
    path = "all_outstanding_requests.jsp?page=1";
    
    return path;
  }
}