package com.awsd.personnel.profile.teacher.handler;

import com.awsd.personnel.*;
import com.awsd.personnel.profile.*;
import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteTeacherCourseRequestHandler    implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Personnel p = null;
    int cid = -1;
    
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

    
    
    if(request.getParameter("cid") != null)
    {
      try
      {
        cid = Integer.parseInt(request.getParameter("cid"));
      }
      catch(NumberFormatException e)
      {
        throw new ProfileException("Delete Teacher Course: invalid grade id format.");
      }
        
    }
    else
    {
      throw new ProfileException("Delete Teacher Course: Grade ID Required.");
    }
    

    p = usr.getPersonnel();

    CourseDB.deleteCoursePersonnel(CourseDB.getCourse(cid), p);

    path = "teacher_viewprofile_courses.jsp?edit=true";  
    
    return path;
  }
}