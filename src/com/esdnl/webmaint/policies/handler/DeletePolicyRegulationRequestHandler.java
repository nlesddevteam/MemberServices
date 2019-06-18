package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeletePolicyRegulationRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
           && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DISTRICTPOLICIES")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if((request.getParameter("cat") == null) || request.getParameter("cat").equals(""))
    {
      request.setAttribute("edit_msg", "CATEGORY CODE IS REQUIRED");
    }
    else if((request.getParameter("code") == null) || request.getParameter("code").equals(""))
    {
      request.setAttribute("edit_msg", "POLICY CODE IS REQUIRED");
    }
    else if((request.getParameter("r_code") == null) || request.getParameter("r_code").equals(""))
    {
      request.setAttribute("edit_msg", "REGULATION CODE IS REQUIRED");
    }
    else
    {
      try
      {
        if(PolicyRegulationDB.deletePolicyRegulation(request.getParameter("cat").toUpperCase(), 
          request.getParameter("code").toUpperCase(), request.getParameter("r_code").toUpperCase()))
        {
          File f = new File(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/regulations/" 
              + request.getParameter("cat").toUpperCase() + "_"
              + request.getParameter("code").toUpperCase() + "_" 
              + request.getParameter("r_code").toUpperCase() + ".pdf");
            
          if(f.exists())
            f.delete();
        }
        request.setAttribute("edit_msg", "POLICY REGULATION DELETED SUCCESSFULLY");
      }
      catch(SQLException e)
      {
        switch(e.getErrorCode())
        {
          case 1:
            request.setAttribute("edit_msg", "CATEGORY " + request.getParameter("cat_code") + " ALREADY EXISTS");
            break;
          default:
            request.setAttribute("edit_msg", e.getMessage());
        }
      }
    }
   
    try
    {
      request.setAttribute("POLICY_REGULATIONS", PolicyRegulationDB.getPolicyRegulations(request.getParameter("cat").toUpperCase(), 
          request.getParameter("code").toUpperCase()));
    }
    catch(SQLException e)
    {
        request.setAttribute("edit_msg", "Regulations for policy " + request.getParameter("cat") + "-" + request.getParameter("code") + " could not be retrieved.");
    }
    
    path = "regulations.jsp";
    
    return path;
  }
}