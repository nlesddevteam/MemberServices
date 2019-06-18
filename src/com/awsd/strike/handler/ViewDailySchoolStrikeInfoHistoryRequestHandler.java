package com.awsd.strike.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.strike.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewDailySchoolStrikeInfoHistoryRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    SchoolStrikeGroup group = null;
    DailySchoolStrikeInfoHistory history = null;
    School school = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("STRIKECENTRAL-ADMINVIEW") 
        || usr.getUserPermissions().containsKey("STRIKECENTRAL-PRINCIPAL-ADMINVIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("school_id") != null)
    {
      school = SchoolDB.getSchool(Integer.parseInt(request.getParameter("school_id")));
      if(school != null)
      {
        request.setAttribute("School", school);
      }
      else
      {
        throw new StrikeException("SCHOOL DOES NOT EXIST IN DATABASE WITH ID: "+ request.getParameter("school_id"));
      }
    }
    else
    {
      throw new StrikeException("SCHOOL ID REQUIRED TO VIEW SCHOOL HISTORY.");
    }
      
    group = SchoolStrikeGroupDB.getSchoolStrikeGroup(school);
    if(group != null)
      request.setAttribute("SchoolStrikeGroup", group);

    history = new DailySchoolStrikeInfoHistory(school);
    if(history != null)
      request.setAttribute("DailySchoolStrikeInfoHistory", history);
    
    path = "schoolstrikestatushistory.jsp";
    
    return path;
  }
}