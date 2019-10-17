package com.awsd.personnel.profile.teacher.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewTeacherProfileRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";

    int nav = 0;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if((usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-TEACHER-VIEW")) || (usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-SECRETARY-VIEW")))
      {
    	//Do nothing but pray  
      } else {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("nav") != null)
    {
        try
        {
          nav = Integer.parseInt(request.getParameter("nav"));  
        }
        catch(NumberFormatException e)
        {
          nav = 1;
        }
    }
    else
    {
      nav = 1;
    }

    switch(nav)
    {
      case 1:
        path = "teacher_viewprofile_name.jsp";
        break;
      case 2:
        path = "teacher_viewprofile_school.jsp";
        break;
      case 3:
      	if(usr.getPersonnel().getSchool() != null)
      		path = "teacher_viewprofile_grades.jsp";
      	else
      	{
      		request.setAttribute("msg", "School selection is required.");
      		path = "teacher_viewprofile_school.jsp";
      	}
        break;
      case 4:
      	if(usr.getPersonnel().getSchool() != null)
      		path = "teacher_viewprofile_courses.jsp";
      	else
      	{
      		request.setAttribute("msg", "School selection is required.");
      		path = "teacher_viewprofile_school.jsp";
      	}
        break;
      default:
        path = "teacher_viewprofile_name.jsp";
    }

    return path;
  }
}