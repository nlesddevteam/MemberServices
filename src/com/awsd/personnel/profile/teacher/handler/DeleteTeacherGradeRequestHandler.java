package com.awsd.personnel.profile.teacher.handler;

import com.awsd.personnel.*;
import com.awsd.personnel.profile.*;
import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteTeacherGradeRequestHandler   implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Personnel p = null;
    Grade g = null;
    int gid = -1;
    
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

    
    
    if(request.getParameter("gid") != null)
    {
      try
      {
        gid = Integer.parseInt(request.getParameter("gid"));
      }
      catch(NumberFormatException e)
      {
        throw new ProfileException("Delete Teacher Grade: invalid grade id format.");
      }
        
    }
    else
    {
      throw new ProfileException("Delete Teacher Grade: Grade ID Required.");
    }
    

    p = usr.getPersonnel();

    g = GradeDB.getGrade(gid);
    
    GradeDB.deleteGradePersonnel(g, p);
    if(g.isHighSchool())
    {
      CourseDB.deleteCoursePersonnel(g, p);
    }

    path = "teacher_viewprofile_grades.jsp?edit=true";  
    
    return path;
  }
}