package com.awsd.security.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class RemoveRoleMemberRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role r = null;
    Personnel p = null;
    User usr = null;
    String rid;
    String op;
    int pid;

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
    
    if(request.getParameter("rid") != null)
    {
      rid = (String)request.getParameter("rid");
      r = RoleDB.getRole(rid);
    }
    else
    {
      throw new RoleException("Role UID not found.");
    }

    if(request.getParameter("pid") != null)
    {
      pid = Integer.parseInt(request.getParameter("pid"));
      p = PersonnelDB.getPersonnel(pid);
    }
    else
    {
      throw new PersonnelException("Personnel ID not found.");
    }
   
    r.getRoleMembership().delete(p);
    
    request.setAttribute("Role", r);

    return "viewrole.jsp";
  }
}