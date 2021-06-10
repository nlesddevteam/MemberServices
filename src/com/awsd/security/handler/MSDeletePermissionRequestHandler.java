package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSDeletePermissionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = "";

    String uid = "", desc;
    HttpSession session = null;
    User usr = null;
    Permission p = null;
    
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
        throw new PermissionException("Permission UID required for deletion.");
      }
      
      request.setAttribute("deleted", new Boolean(false));

      if(request.getParameter("confirmed") != null)
      {
        boolean flag = PermissionDB.deletePermission(uid);

        if(flag)
        {
          request.setAttribute("msgOK", "SUCCESS: Permission deleted successfully.");
          request.setAttribute("deleted", new Boolean(true));
        }
        else
        {
          request.setAttribute("msgERR", "ERROR: Permission deletion unsuccessful.");
        }
      }
      else
      {
        p = PermissionDB.getPermission(uid);
        request.setAttribute("Permission", p);
      }
      
      //path = "modifyevent.jsp";
    }
    catch(PermissionException e)
    {
      request.setAttribute("msg", e.getMessage());
      p = PermissionDB.getPermission(uid);
      request.setAttribute("Permission", p);
    }
    //return path;
    return "viewpermissions.jsp";
  }
}