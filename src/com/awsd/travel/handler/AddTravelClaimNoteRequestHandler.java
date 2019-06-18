package com.awsd.travel.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddTravelClaimNoteRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")
        || usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")))
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
        request.setAttribute("TRAVELCLAIM", claim);
        
        if(request.getParameter("op") != null)
        {
          if(request.getParameter("op").equalsIgnoreCase("CONFIRM"))
          {
            if((request.getParameter("note") != null) && !request.getParameter("note").trim().equals(""))
            {
              if(TravelClaimNoteDB.addClaimNote(claim, new TravelClaimNote(usr.getPersonnel(), request.getParameter("note"))))
              {
                request.setAttribute("msg","Note Added Successfully.");
              }
              else
              {
                request.setAttribute("msg","Note could not be added.");
              }
            }
            else
            {
              request.setAttribute("msg","Note cannot be empty.");
            }
          }
        }
        path = "add_claim_note.jsp";
      }
      else
      {
           path = "claim_error.jsp?msg=Claim cound not be found.";
      }
    }
    else
      path = "claim_error.jsp?msg=Claim ID Required For ADD Note Operation.";
    
    return path;
  }
}