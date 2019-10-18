package com.awsd.personnel.profile.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class PersonnelProfileConfirmedRequestHandler   implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Personnel p = null;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if((usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-TEACHER-VIEW")) || (usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-SECRETARY-VIEW")))
      {
    	//Do nothing but pray  
      } else {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    p = usr.getPersonnel();

    if(p.getViewOnNextLogon() != null)
    {
      System.err.println(usr.getLotusUserFullName() + " PROFILE CONFIRMED");
      p.setViewOnNextLogon(null);
      path = "/MemberServices/memberservices_frame.jsp";
      request.setAttribute("REDIRECT", new Boolean(true));
    }
    else
    {
      path = "MemberServices/memberServices.html";
    }
    
    return path;
  }
}