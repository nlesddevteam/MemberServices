package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSViewRoleRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role r = null;
    User usr = null;
    String uid;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    if(request.getParameter("uid") != null)
    {
      uid = (String)request.getParameter("uid");
      r = RoleDB.getRole(uid);
    }
    else
    {
      throw new RoleException("Role UID not found.");
    }
  
    request.setAttribute("Role", r);

    return "viewrole.jsp";
  }
}