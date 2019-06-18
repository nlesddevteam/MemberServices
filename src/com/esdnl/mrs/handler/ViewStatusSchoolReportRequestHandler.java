package com.esdnl.mrs.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.school.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewStatusSchoolReportRequestHandler  implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingMaintenanceRequestsPagesByStatusSchool(request.getParameter("status"), SchoolDB.getSchool(Integer.parseInt(request.getParameter("school"))), 10));
      
    path = "all_outstanding_requests.jsp?page=1";
    
    return path;
  }
}