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


public class PersonnelAdminSchoolChangeRequestHandler implements RequestHandler
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
      if(request.getParameter("school.schoolID") == null)
      {
        throw new AdminException("School ID Required.");
      }
      else
      {
        sid = Integer.parseInt(request.getParameter("school.schoolID"));
      }
    
      s = SchoolDB.getSchool(sid);
      tmp.setSchool(s);

      path = "personnel_admin_view.jsp";
    }
    
    return path;
  }
}