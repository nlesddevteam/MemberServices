package com.awsd.admin.apps.personnel.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.school.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class PersonnelAdminNameChangeRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel tmp = null;
    School s = null;
    int pid, sid;
    String firstName=null, lastName=null, userName=null, email=null; 
    
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

    if(request.getParameter("personnelID") == null)
    {
      throw new AdminException("Personnel ID Required.");
    }
    else
    {
      pid = Integer.parseInt(request.getParameter("personnelID"));
    }

    tmp = PersonnelDB.getPersonnel(pid);

    if(request.getParameter("update") == null)
    {        
      request.setAttribute("Personnel", tmp);
      request.setAttribute("ALL_SCHOOLS", new Schools());

      path = "personnel_admin_school_change.jsp";
    }
    else
    {
      if((firstName = request.getParameter("firstName"))== null)
      	throw new AdminException("FIRSTNAME Required.");
      
      if((lastName = request.getParameter("lastName"))== null)
      	throw new AdminException("LASTNAME Required.");
      
      if((userName = request.getParameter("userName"))== null)
      	throw new AdminException("USERNAME Required.");
      
      if((email = request.getParameter("emailAddress"))== null)
      	throw new AdminException("EMAIL ADDRESS Required.");
    	
    	if(request.getParameter("school.schoolID") == null)
      {
        throw new AdminException("School ID Required.");
      }
      else
      {
        sid = Integer.parseInt(request.getParameter("school.schoolID"));
      }

    	tmp.setFirstName(firstName);
    	tmp.setLastName(lastName);
    	tmp.setUserName(userName);
    	tmp.setEmailAddress(email);
    	
      s = SchoolDB.getSchool(sid);
      tmp.setSchool(s);

      path = "personnel_admin_view.jsp";
    }
    
    return path;
  }
}