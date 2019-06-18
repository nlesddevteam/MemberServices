package com.awsd.travel.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteTravelClaimRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    TravelClaim claim = null;
    String op = "";
    int id = -1;
    boolean check = false;
    boolean isAdmin = false;
        
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

    if(id < 1)
    {
      throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR DELETE OPERATION.  >>>>>");
    }
    else
    { 
    	isAdmin = usr.getUserRoles().containsKey("ADMINISTRATOR");
    	
      claim = TravelClaimDB.getClaim(id);
      if(claim instanceof PDTravelClaim)
        ((PDTravelClaim)claim).getPD();
      request.setAttribute("TRAVELCLAIM", claim);

      if(!isAdmin && (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()))
      {
        request.setAttribute("msg", "You do not have permission to delete this travel claim.");
        request.setAttribute("NOPERMISSION", new Boolean(true));
      }
      else
      {      
        op = request.getParameter("op");
        if(op != null)
        {
          if(op.equalsIgnoreCase("CONFIRM"))
          {
            check = TravelClaimDB.deleteClaim(claim);

            if(check)
            {
              request.setAttribute("RESULT", "SUCCESS");
              request.setAttribute("msg", "Claim deleted successfully.");
            }
            else
            {
              request.setAttribute("RESULT", "FAILED");
              request.setAttribute("msg", "Claim could not be deleted.");
            }
          }
          else
          {
            request.setAttribute("msg", "Invalid operation.");
          }
        }
      }
    }
    
    path = "delete_claim.jsp";
    
    return path;
  }
}