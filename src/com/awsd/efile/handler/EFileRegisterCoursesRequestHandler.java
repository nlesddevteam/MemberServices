package com.awsd.efile.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class EFileRegisterCoursesRequestHandler   implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Courses courses = null;
    Courses ucourses = null;
    Course c = null;
    Iterator iter = null;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    courses = new Courses(true);
    ucourses = new Courses(false);

    iter = courses.iterator();
    while(iter.hasNext())
    {
      c = (Course)iter.next();
      

      if(request.getParameter("C"+c.getCourseID()) != null)
      {
        ucourses.add(c);
      }
    }

    CourseDB.addCoursePersonnel(ucourses,usr.getPersonnel());

    path = "choose.jsp"; 

    return path;
  }
}