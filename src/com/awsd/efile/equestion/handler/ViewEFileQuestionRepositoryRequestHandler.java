package com.awsd.efile.equestion.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewEFileQuestionRepositoryRequestHandler implements RequestHandler 
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Grades grades = null;
    Courses courses = null;

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

    if(!usr.getUserPermissions().containsKey("EFILE-UNRESTRICTED-SEARCH"))
    {
      grades = new Grades(usr);

      if(grades.isEmpty())
      {
        path = "/MemberServices/EFile/Registration_Grades.jsp";
      }
      else
      {
        path = "viewQuestionRepository.jsp"; 
      }
    }
    else
    {
      path = "viewQuestionRepository.jsp"; 
    }
      
    return path;
  }
}