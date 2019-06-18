package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddPolicyCategoryRequestHandler implements RequestHandler
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
    
    if((request.getParameter("cat_code") == null) || request.getParameter("cat_code").equals(""))
    {
      request.setAttribute("msg", "CATEGORY CODE IS REQUIRED");
    }
    else if((request.getParameter("cat_title") == null) || request.getParameter("cat_title").equals(""))
    {
      request.setAttribute("msg", "CATEGORY TITLE IS REQUIRED");
    }
    else if(request.getParameter("cat_code").length() > 10)
    {
      request.setAttribute("msg", "CATEGORY CODE HAS MAX LENGHT OF 10 CHARACTERS");
    }
    else if(request.getParameter("cat_title").length() > 100)
    {
      request.setAttribute("msg", "CATEGORY TITLE HAS MAX LENGHT OF 100 CHARACTERS");
    }
    else
    {
      try
      {
        PolicyCategoryDB.addPolicyCategory(new PolicyCategory(request.getParameter("cat_code").toUpperCase(), request.getParameter("cat_title").toUpperCase()));
        request.setAttribute("msg", "CATEGORY ADD SUCCESSFULLY");
      }
      catch(SQLException e)
      {
        switch(e.getErrorCode())
        {
          case 1:
            request.setAttribute("msg", "CATEGORY " + request.getParameter("cat_code") + " ALREADY EXISTS");
            break;
          default:
            request.setAttribute("msg", e.getMessage());
        }
      }
    }
   
    request.setAttribute("POLICY_CATEGORIES", PolicyCategoryDB.getPolicyCategories());
    path = "policy_categories.jsp";
    
    return path;
  }
}