package com.esdnl.mrs.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class PrintRequestDetailsRequestHandler  implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")
        || usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")
        || usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    request.setAttribute("MAINT_REQUEST", MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req"))));
    request.setAttribute("REQUEST_COMMENTS", (RequestComment[])MaintenanceRequestDB.getMaintenanceRequestComments(Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
    request.setAttribute("ASSIGNED_PERSONNEL", PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
    request.setAttribute("ASSIGNED_VENDORS", VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
    path = "print_request_details.jsp";
    
    return path;
  }
}