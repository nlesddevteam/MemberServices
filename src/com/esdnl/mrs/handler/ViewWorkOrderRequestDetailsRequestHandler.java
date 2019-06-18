package com.esdnl.mrs.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewWorkOrderRequestDetailsRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-WORKORDERS-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if(request.getParameter("op") != null)
    {
      if(request.getParameter("op").equalsIgnoreCase("CONFIRM"))
      {
        if((request.getParameter("req") == null) || (request.getParameter("req").equals("")))
        {
          request.setAttribute("msg", "REQUEST ID REQUIRED");
        }
        else if((request.getParameter("new_status") == null) || (request.getParameter("new_status").equals("")))
        {
          request.setAttribute("msg", "REQUEST STATUS REQUIRED");
        }
        else
        {
          req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")));
          
            //form is completed correctly
            req.setCurrentStatus(new StatusCode(request.getParameter("new_status")));
            
            MaintenanceRequestDB.updateAdminMaintRequest(req);
        }
        request.setAttribute("MAINT_REQUEST", MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req"))));
        request.setAttribute("STATUS_CODES", (StatusCode[])StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
        request.setAttribute("REQUEST_COMMENTS", (RequestComment[])MaintenanceRequestDB.getMaintenanceRequestComments(Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
        request.setAttribute("ASSIGNED_PERSONNEL", PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
        request.setAttribute("ASSIGNED_VENDORS", VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
        
        path = "workorder_request_details.jsp"; 
      }
      else
      {
        request.setAttribute("msg", "INVALID OPTION");
        
        path = "error_message.jsp";
      }
    }
    else
    {    
      if(request.getParameter("req") != null)
      {
        req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")), usr.getPersonnel());
        request.setAttribute("MAINT_REQUEST", req);
        request.setAttribute("STATUS_CODES", (StatusCode[])StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
        request.setAttribute("REQUEST_COMMENTS", (RequestComment[])MaintenanceRequestDB.getMaintenanceRequestComments(Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
        request.setAttribute("ASSIGNED_PERSONNEL", PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
        request.setAttribute("ASSIGNED_VENDORS", VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
        
        path = "workorder_request_details.jsp"; 
      }
      else
      {
        request.setAttribute("msg", "REQUEST ID REQUIRED");
        
        path = "error_message.jsp";
      }
    }
    
    return path;
  }
}