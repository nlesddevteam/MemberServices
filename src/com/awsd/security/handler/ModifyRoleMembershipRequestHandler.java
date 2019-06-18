package com.awsd.security.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ModifyRoleMembershipRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    Role r = null;
    Personnel p = null;
    User usr = null;
    String uid;
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
      	String ids[] = request.getParameterValues("available");
      	for(int i=0; i < ids.length; i++){
      		pid = Integer.parseInt(ids[i]);
      		r.getRoleMembership().add(PersonnelDB.getPersonnel(pid));
      	}
      }
      else if(op.equals("REMOVE"))
      {
      	String ids[] = request.getParameterValues("assigned");
      	for(int i=0; i < ids.length; i++){
      		pid = Integer.parseInt(ids[i]);
      		r.getRoleMembership().delete(PersonnelDB.getPersonnel(pid));
      	}
      }
    }
  
    request.setAttribute("Role", r);

    return "membership.jsp";
  }
}