package com.awsd.admin.apps.personnel.handler;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.awsd.admin.*;
import com.awsd.servlet.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.personnel.*;


public class PersonnelAdminChangeRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel tmp = null;
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

    if(request.getParameter("pid") == null)
    {
      throw new AdminException("Personnel ID Required.");
    }
    else
    {
      pid = Integer.parseInt(request.getParameter("pid"));
    }

    tmp = PersonnelDB.getPersonnel(pid);

    if(request.getParameter("update") == null)
    {        
      request.setAttribute("Personnel", tmp);

      path = "personnel_admin_change.jsp";
    }
    else
    {
    	String firstname = request.getParameter("firstname");
    	String lastname = request.getParameter("lastname");
    	String username = request.getParameter("username");
    	String email = request.getParameter("email");
    	
    	tmp.setFirstName(firstname.toUpperCase());
    	tmp.setLastName(lastname.toUpperCase());
    	tmp.setUserName(username);
    	tmp.setEmailAddress(email);
    	
    	PersonnelDB.updatePersonnel(tmp);

      path = "personnel_admin_view.jsp";
    }
    
    return path;
  }
}