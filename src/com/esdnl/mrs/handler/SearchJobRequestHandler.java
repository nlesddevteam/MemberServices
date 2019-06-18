package com.esdnl.mrs.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SearchJobRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    MaintenanceRequest req = null;
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW"))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if(request.getParameter("op") != null)
    {
      if(request.getParameter("op").equalsIgnoreCase("CONFIRM"))
      {
        if(request.getParameter("req") != null)
        {
          req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")), usr.getPersonnel());
          request.setAttribute("MAINT_REQUEST", req);
          
          if(req != null)
          {
            request.setAttribute("MAX_PRIORITY", new Integer(((MaintenanceRequest[])MaintenanceRequestDB.getOutstandingMaintenanceRequests(req.getSchool()).get(0)).length));
            request.setAttribute("STATUS_CODES", (StatusCode[])StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
            request.setAttribute("MAINT_PERSONNEL", PersonnelDB.getMaintenanceAssigmentPersonnel());
            request.setAttribute("VENDORS", (Vendor[]) VendorDB.getVendors().toArray(new Vendor[0]));
            request.setAttribute("REQUEST_COMMENTS", (RequestComment[])MaintenanceRequestDB.getMaintenanceRequestComments(Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
            request.setAttribute("CAPITAL_TYPES", (CapitalType[])CapitalTypeDB.getCapitalTypes().toArray(new CapitalType[0]));
            request.setAttribute("CATEGORIES", (RequestCategory[])RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
            request.setAttribute("ASSIGNED_PERSONNEL", PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
            request.setAttribute("ASSIGNED_VENDORS", VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
            request.setAttribute("REQUEST_TYPES", (RequestType[])RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));
            request.setAttribute("JOB_FOUND", "true");
          }
          else
          {
            request.setAttribute("JOB_FOUND", "false");
          }
          
          path = "admin_request_details.jsp"; 
        }
      }
    }

    return path;
  }
}