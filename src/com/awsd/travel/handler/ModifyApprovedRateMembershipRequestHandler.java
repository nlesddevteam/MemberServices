package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ModifyApprovedRateMembershipRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role tcRole = null;
    Personnel p = null;
    User usr = null;   
    String op;
    int pid;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
     
      if(!(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-ADMIN")))
      {
        throw new SecurityException("Illegal Access [" + usr.getPersonnel().getFullNameReverse()+ "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
             
   tcRole= RoleDB.getRole("TRAVELCLAIM APPROVED RATE");    
    op = request.getParameter("op");
    if(op != null)
    {
      if(op.equals("ADD"))
      {
      	String ids[] = request.getParameterValues("available");
      	for(int i=0; i < ids.length; i++){
      		pid = Integer.parseInt(ids[i]);
      		tcRole.getRoleMembership().add(PersonnelDB.getPersonnel(pid));
      		request.setAttribute("msgOK", "SUCCESS: Member Added to Higher Rate.");
      	}
      }
      else if(op.equals("REMOVE"))
      {
      	String ids[] = request.getParameterValues("assigned");
      	for(int i=0; i < ids.length; i++){
      		pid = Integer.parseInt(ids[i]);
      		tcRole.getRoleMembership().delete(PersonnelDB.getPersonnel(pid));
      		request.setAttribute("msgOK", "SUCCESS: Member Removed from Higher Rate.");
      	}
      }
    }
  
    //request.setAttribute("Role", tcRole);
  return "travel_rates.jsp";
   
  }
}