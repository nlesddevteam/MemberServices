package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSViewPermissionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Permission p = null;
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
      p = PermissionDB.getPermission(uid);
    }
    else
    {
      throw new PermissionException("Permission UID not found.");
    }
  
    request.setAttribute("Permission", p);

    return "viewpermission.jsp";
  }
}