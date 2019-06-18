package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewPolicyRegulationsRequestHandler  implements RequestHandler
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
   
   try
   {
      request.setAttribute("POLICY_REGULATIONS", PolicyRegulationDB.getPolicyRegulations(request.getParameter("cat"), request.getParameter("code"))); 
   }
   catch(SQLException e)
   {
     request.setAttribute("edit_msg", "Regulations for policy " + request.getParameter("cat") + "-" + request.getParameter("code") + " could not be retrieved.");
   }
    
    path = "regulations.jsp";
    
    return path;
  }
}