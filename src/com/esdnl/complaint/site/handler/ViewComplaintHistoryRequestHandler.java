package com.esdnl.complaint.site.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.school.*;

import com.esdnl.util.*;
import com.esdnl.complaint.model.bean.*;
import com.esdnl.complaint.model.constant.*;
import com.esdnl.complaint.database.*;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewComplaintHistoryRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    ComplaintBean complaint = null;
    
    try
    {
      session = request.getSession(false);
      if((session != null) && (session.getAttribute("usr") != null))
      {
        usr = (User) session.getAttribute("usr");
        if(!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
          || usr.getUserPermissions().containsKey("COMPLAINT-ADMIN")
          || usr.getUserPermissions().containsKey("COMPLAINT-VIEW")))
        {
          throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
        }
      }
      
      String id = request.getParameter("id");
      if(StringUtils.isEmpty(id))
        request.setAttribute("msg", "ERROR: Complaint ID is require to view a complaint record.");
      else
      {
        complaint = ComplaintManager.getComplaintBean(Integer.parseInt(id));
        if(!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
          || usr.getUserPermissions().containsKey("COMPLAINT-ADMIN"))
          && usr.getUserPermissions().containsKey("COMPLAINT-VIEW") && 
          !ComplaintManager.isComplaintBeanAssignedTo(complaint, usr.getPersonnel()))
          request.setAttribute("msg", "ERROR: You do not have the permissions necessary to view this complaint record.");
        else
        {
          request.setAttribute("COMPLAINT_BEAN", complaint); 
        }
      }  
      path = "admin_complaint_history.jsp";
    }
    catch(ComplaintException e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "admin_complaint_summary.jsp";
    }
    
    return path;
  }
}