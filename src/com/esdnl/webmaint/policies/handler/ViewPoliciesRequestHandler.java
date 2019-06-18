package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewPoliciesRequestHandler implements RequestHandler
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
   
    request.setAttribute("POLICIES", PolicyDB.getPolicies());
    path = "policies.jsp";
    
    return path;
  }
}