package com.awsd.travel.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewTravelClaimHistoryRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";

    TravelClaim claim = null;
    int id = -1;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW")))
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
      claim = TravelClaimDB.getClaim(id);

      if(claim != null)
      {
        if((usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID())
          ||usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))
        {
          
          request.setAttribute("SUPERVISOR", "true");
          if((claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED)
            || claim.getCurrentStatus().equals(TravelClaimStatus.REVIEWED))
            && !usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))
          {
            claim.setCurrentStatus(usr.getPersonnel(), TravelClaimStatus.REVIEWED);
          }
          request.setAttribute("TRAVELCLAIM", claim);
          path = "claim_history.jsp";
        }
        else if(usr.getPersonnel().getPersonnelID() == claim.getPersonnel().getPersonnelID())
        {
          request.setAttribute("TRAVELCLAIM", claim);
          path = "claim_history.jsp";
        }
        
        else
        {
           path = "claim_error.jsp?msg=You do not have permission to view this travel claim.";
        }
      }
      else
      {
        path = "claim_error.jsp?msg=Claim cound not be found.";
      }
    }
    else
      path = "claim_error.jsp?msg=Claim cound not be found.";
    
    return path;
  }
}