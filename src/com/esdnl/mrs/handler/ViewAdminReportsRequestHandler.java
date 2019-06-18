package com.esdnl.mrs.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewAdminReportsRequestHandler implements RequestHandler
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
    
    request.setAttribute("REQUEST_TYPES", (RequestType[])RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));
    request.setAttribute("STATUS_CODES", (StatusCode[])StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
    request.setAttribute("REQUEST_CATEGORIES", (RequestCategory[])RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
    
    if((usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW")))
    {
      request.setAttribute("SCHOOLS", (School[])MaintenanceRequestDB.getRegionalSchools(usr.getPersonnel()).toArray(new School[0]));
    }
    else
    {
      request.setAttribute("SCHOOLS", (School[])SchoolDB.getSchools().toArray(new School[0]));
    }
      
    path = "admin_reports.jsp";
    
    return path;
  }
}