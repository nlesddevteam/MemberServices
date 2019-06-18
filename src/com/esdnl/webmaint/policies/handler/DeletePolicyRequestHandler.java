package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeletePolicyRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    File f = null;
    String path = "";
    Iterator iter = null;
    PolicyRegulation reg= null;
    
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
    if((request.getParameter("code") == null) || request.getParameter("code").equals(""))
    {
      request.setAttribute("edit_msg", "POLICY CODE IS REQUIRED");
    }
    else
    {
      try
      {
        iter = PolicyRegulationDB.getPolicyRegulations(request.getParameter("cat").toUpperCase(), request.getParameter("code").toUpperCase()).iterator();
        while(iter.hasNext())
        {
          reg = (PolicyRegulation) iter.next();
          
          if(PolicyRegulationDB.deletePolicyRegulation(reg.getCategoryCode(), reg.getPolicyCode(), reg.getRegulationCode()))
          {
            f = new File(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/regulations/" 
              + reg.getCategoryCode() + "_" + reg.getPolicyCode() + "_"  + reg.getRegulationCode() + ".pdf");
            
            if(f.exists())
              f.delete();
          }
        }
        
        if(PolicyDB.deletePolicy(request.getParameter("cat").toUpperCase(), request.getParameter("code").toUpperCase()))
        {
          f = new File(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/"
                + request.getParameter("cat").toUpperCase() + "_" + request.getParameter("code").toUpperCase() 
                + ".pdf");
        
          if(f.exists())
            f.delete();
        }
          
        request.setAttribute("edit_msg", "POLICY DELETED SUCCESSFULLY");
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
   
    request.setAttribute("POLICIES", PolicyDB.getPolicies());
    path = "policies.jsp";
    
    return path;
  }
}