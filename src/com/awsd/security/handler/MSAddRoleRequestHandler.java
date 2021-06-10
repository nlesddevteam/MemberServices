package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSAddRoleRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String uid, desc;
    Role r = null;
    boolean added = false;
    HttpSession session = null;
    User usr = null;
    
    try
    {
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

      if(request.getParameter("passthrough") == null)
      { 
        uid = (String) request.getParameter("uid");
        if(uid == null)
        {
          throw new RoleException("Role UID Not Provided.");
        }

        desc = (String) request.getParameter("description");
        if(desc==null)
        {
          throw new RoleException("Role Description Not Provided");
        }

        r = new Role(uid, desc);
        added = RoleDB.addRole(r);

        if(added)
        {
          request.setAttribute("msgOK", "SUCCESS: Role Added Successfully.");  
        }
        else
        {
          request.setAttribute("msgERR", "ERROR: Role Adding Unsuccessful.");  
        }
      }
    }
    catch(RoleException e)
    {
      request.setAttribute("msgERR", e.getMessage());
    }
    
    return "addrole.jsp";
  }
}