package com.awsd.personnel.profile.teacher.handler;

import com.awsd.personnel.*;
import com.awsd.personnel.profile.*;
import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ModifyTeacherSchoolRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Personnel p = null;
    int sid = -1;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-TEACHER-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    
    if(request.getParameter("school") != null)
    {
      try
      {
        sid = Integer.parseInt(request.getParameter("school"));
      }
      catch(NumberFormatException e)
      {
        throw new ProfileException("Modify Teacher School: invalid school id format.");
      }
        
    }
    else
    {
      throw new ProfileException("Modify Teacher School: School Required.");
    }
    

    p = usr.getPersonnel();

    p.setSchool(SchoolDB.getSchool(sid));

    path = "teacher_viewprofile_school.jsp";  
    
    return path;
  }
}