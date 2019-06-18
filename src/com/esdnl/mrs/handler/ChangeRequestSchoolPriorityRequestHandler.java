package com.esdnl.mrs.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ChangeRequestSchoolPriorityRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String username = "";
    String hash = "";
    Personnel p = null;
    MaintenanceRequest req = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")
        || usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
   
    if((request.getParameter("req") == null) || (request.getParameter("req").equals("")))
    {
      request.setAttribute("msg", "REQUEST ID is require.");
    }
    else if((request.getParameter("priority") == null) || (request.getParameter("priority").equals("")))
    {
      request.setAttribute("msg", "NEW PRIORITY is require.");
    }
    else
    {
      MaintenanceRequestDB.changeSchoolPriority(Integer.parseInt(request.getParameter("req")), Integer.parseInt(request.getParameter("priority")));
    }
    
    if(request.getParameter("admin")!= null)
    {
      Vector maint =null;
      (maint = (PersonnelDB.getPersonnelList(RoleDB.getRole("SCHOOL MAINTENANCE")))).addAll(PersonnelDB.getPersonnelList(RoleDB.getRole("SUPERVISOR MAINTENANCE SERVICES")));
      req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")), usr.getPersonnel());
      request.setAttribute("MAINT_REQUEST", req);
      request.setAttribute("MAX_PRIORITY", new Integer(MaintenanceRequestDB.getOutstandingMaintenanceRequests(req.getSchool()).size()));
      request.setAttribute("STATUS_CODES", StatusCodeDB.getStatusCodes());
      request.setAttribute("MAINT_PERSONNEL", maint);
      request.setAttribute("VENDORS", VendorDB.getVendors());
      request.setAttribute("REQUEST_COMMENTS", MaintenanceRequestDB.getMaintenanceRequestComments(Integer.parseInt(request.getParameter("req"))));
      request.setAttribute("CAPITAL_TYPES", CapitalTypeDB.getCapitalTypes());
      request.setAttribute("CATEGORIES", RequestCategoryDB.getRequestCategories());
      request.setAttribute("ASSIGNED_PERSONNEL", PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
      request.setAttribute("ASSIGNED_VENDORS", VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
      request.setAttribute("REQUEST_TYPES", RequestTypeDB.getRequestTypes());
      path = "admin_request_details.jsp"; 
    }
    else
    {
      session.setAttribute("OUTSTANDING_REQUESTS", MaintenanceRequestDB.getOutstandingMaintenanceRequests(usr.getPersonnel().getSchool()));
      path = "school_outstanding_requests.jsp";
    }
    
    return path;
  }
}