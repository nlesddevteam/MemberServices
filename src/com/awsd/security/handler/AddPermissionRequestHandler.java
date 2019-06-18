package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddPermissionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String uid, desc;
    Permission p = null;
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
          throw new PermissionException("Permission UID Not Provided.");
        }

        desc = (String) request.getParameter("description");
        if(desc==null)
        {
          throw new PermissionException("Permission Description Not Provided");
        }

        p = new Permission(uid, desc);
        added = PermissionDB.addPermission(p);

        if(added)
        {
          request.setAttribute("msg", "Permission Added Successfully.");  
        }
        else
        {
          request.setAttribute("msg", "Permission Adding Unsuccessful.");  
        }
      }
    }
    catch(PermissionException e)
    {
      request.setAttribute("msg", e.getMessage());
    }
    
    return "addpermission.jsp";
  }
}