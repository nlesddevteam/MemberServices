package com.awsd.travel.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteTravelClaimItemRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    TravelClaim claim = null;
    TravelClaimItem item = null;
    String op = "";
    int cid = -1, iid = -1;
    boolean check = false;
        
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
      cid = Integer.parseInt(request.getParameter("cid"));
    }
    catch(NumberFormatException e)
    {
      cid = -1;
    }

    try
    {
      iid = Integer.parseInt(request.getParameter("iid"));
    }
    catch(NumberFormatException e)
    {
      iid = -1;
    }

    if(cid < 1)
    {
      throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR DELETE ITEM OPERATION.  >>>>>");
    }
    else if(iid < 1)
    {
      throw new TravelClaimException("<<<<< ITEM ID IS REQUIRED FOR DELETE ITEM OPERATION.  >>>>>");
    }
    else
    { 
      claim = TravelClaimDB.getClaim(cid);
      request.setAttribute("TRAVELCLAIM", claim);

      item = TravelClaimItemsDB.getClaimItem(iid);
      request.setAttribute("TRAVELCLAIMITEM", item);

      if(claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID())
      {
        request.setAttribute("msg", "You do not have permission to perform delete operation.");
        request.setAttribute("NOPERMISSION", new Boolean(true));
      }
      else
      {      
        op = request.getParameter("op");
        if(op != null)
        {
          if(op.equalsIgnoreCase("CONFIRM"))
          {
            check = TravelClaimItemsDB.deleteClaimItem(iid);

            if(check)
            {
              request.setAttribute("RESULT", "SUCCESS");
              request.setAttribute("msg", "Item deleted successfully.");
            }
            else
            {
              request.setAttribute("RESULT", "FAILED");
              request.setAttribute("msg", "Item could not be deleted.");
            }
          }
          else
          {
            request.setAttribute("msg", "Invalid operation.");
          }
        }
      }
    }
    
    path = "delete_claim_item.jsp";
    
    return path;
  }
}