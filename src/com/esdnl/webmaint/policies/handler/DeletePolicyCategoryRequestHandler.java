package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeletePolicyCategoryRequestHandler implements RequestHandler
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
    
    if((request.getParameter("code") == null) || request.getParameter("code").equals(""))
    {
      request.setAttribute("edit_msg", "CATEGORY CODE IS REQUIRED");
    }
    else
    {
      try
      {
        PolicyCategoryDB.deletePolicyCategory(request.getParameter("code").toUpperCase());
        request.setAttribute("edit_msg", "CATEGORY DELETED SUCCESSFULLY");
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
   
    request.setAttribute("POLICY_CATEGORIES", PolicyCategoryDB.getPolicyCategories());
    path = "policy_categories.jsp";
    
    return path;
  }
}