package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SupervisorViewAllClaimsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    Personnel p = null;
    int id = -1;
    String path = "";
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") 
      || usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    try
    {
      id = Integer.parseInt(request.getParameter("id"));
    }
    catch(NumberFormatException e)
    {
      id = -1;
    }

    if(id > 0)
    {
      p = PersonnelDB.getPersonnel(id);

      if(p != null)
      {
        request.setAttribute("PERSONNEL", p);
        path = "all_claims_view.jsp";
      }
      else
      {
        path = "claim_error.jsp?msg=employee cound not be found.";
      }
    }
    else
    {
      path = "claim_error.jsp?msg=Personnel ID is required to view all employee claims.";
    }
    
    return path;
  }
}