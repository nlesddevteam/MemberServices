package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RemoveApprovedRateMemberRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role tcRole = null;
    Personnel p = null;
    User usr = null;        
    int pid;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-ADMIN")))
      {
        throw new SecurityException("Illegal Access [" + usr.getPersonnel().getFullNameReverse() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
      
    tcRole= RoleDB.getRole("TRAVELCLAIM APPROVED RATE");       

    if(request.getParameter("pid") != null)
    {
      pid = Integer.parseInt(request.getParameter("pid"));
      p = PersonnelDB.getPersonnel(pid);
    }
    else
    {
      throw new PersonnelException("Personnel ID not found.");
    }
   
    tcRole.getRoleMembership().delete(p);    
    //request.setAttribute("Role",tcRole);
    request.setAttribute("msgOK", "SUCCESS: Member Removed from Higher Rate.");
    return "travel_rates.jsp";
  }
}