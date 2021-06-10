package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSModifyRolePermissionsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role r = null;
    Permission p = null;
    User usr = null;
    String uid;
    String op;
    String pid;

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
    op = request.getParameter("op");
    if(op != null)
    {
      if(op.equals("ADD"))
      {
        pid = request.getParameter("available");
        r.getRolePermissions().add(PermissionDB.getPermission(pid));
        request.setAttribute("msgOK", "SUCCESS: Permission successfully added to this role.");
      }
      else if(op.equals("REMOVE"))
      {
        pid = request.getParameter("assigned");
        r.getRolePermissions().delete(PermissionDB.getPermission(pid));
        request.setAttribute("msgOK", "SUCCESS: Permission successfully removed from this role.");
      } else {
    	  request.setAttribute("msgERR", "ERROR: There was a problem adding the permission. Please try again..");
      }
    }
  
    request.setAttribute("Role", r);

    return "permissions.jsp";
  }
}