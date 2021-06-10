package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSDeleteRoleRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = "";

    String uid = "", desc;
    HttpSession session = null;
    User usr = null;
    Role r = null;
    
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
      
      if(request.getParameter("uid") != null)
      {
        uid = (String)request.getParameter("uid");
      }
      else
      {
        throw new RoleException("Role UID required for deletion.");
      }
      
      request.setAttribute("deleted", new Boolean(false));

      if(request.getParameter("confirmed") != null)
      {
        boolean flag = RoleDB.deleteRole(uid);

        if(flag)
        {
          request.setAttribute("msgOK", "SUCCESS: Role deleted successfully.");
          request.setAttribute("deleted", new Boolean(true));
        }
        else
        {
          request.setAttribute("msgERR", "ERROR: Role deletion unsuccessful.");
        }
      }
      else
      {
        r = RoleDB.getRole(uid);
        request.setAttribute("Role", r);
      }
    }
    catch(RoleException e)
    {
      request.setAttribute("msgERR", e.getMessage());
      r = RoleDB.getRole(uid);
      request.setAttribute("Role", r);
    }
    //return path;
    return "deleterole.jsp";
  }
}