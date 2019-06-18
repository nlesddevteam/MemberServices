package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteRequestRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    ArrayList pages = null;
    MaintenanceRequest[] page_reqs = null;
    ArrayList page = null;
    MaintenanceRequest req = null;
    int req_id = -1;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    
    pages = (ArrayList)session.getAttribute("OUTSTANDING_REQUESTS");
    
    if((request.getParameter("req") == null) || (request.getParameter("req").equals("")))
    {
      request.setAttribute("msg", "REQUEST ID is require to CANCEL.");
    }
    else
    {
      req_id = Integer.parseInt(request.getParameter("req"));
      
      if(MaintenanceRequestDB.deleteMaintenanceRequest(req_id))
      {
        page = new ArrayList();
        
        page_reqs = (MaintenanceRequest[]) pages.get(Integer.parseInt(request.getParameter("page"))-1);
    
        for(int i=0; i < page_reqs.length; i++)
        {
          if(page_reqs[i].getRequestID() != req_id)
            page.add(page_reqs[i]);
        }
        
        pages.set(Integer.parseInt(request.getParameter("page"))-1, (MaintenanceRequest[])page.toArray(new MaintenanceRequest[0]));
      }
    }
    
    session.setAttribute("OUTSTANDING_REQUESTS", pages);
    path = "all_outstanding_requests.jsp";
    
    return path;
  }
}