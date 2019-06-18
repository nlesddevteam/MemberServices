package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewSchoolMemberEventsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    SchoolRegisteredEvents schoolview = null;
    School s = null;
    User usr = null;
    int id;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("CALENDAR-VIEW")&&usr.getUserPermissions().containsKey("CALENDAR-VIEW-SCHOOL-ENROLLMENT")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("id") != null)
    {
      id = Integer.parseInt((String)request.getParameter("id"));
      s = SchoolDB.getSchool(id);
    }
    else
    {
      throw new SchoolException("School ID required.");
    }
   
    schoolview = new SchoolRegisteredEvents(s);
    request.setAttribute("SchoolRegisteredEvents", schoolview);

    return "schoolregisteredevents.jsp";
  }
}