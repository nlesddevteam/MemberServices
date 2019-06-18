package com.awsd.strike.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.strike.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewStrikeCentralPrincipalAdminRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    SchoolStrikeGroup group = null;
    DailySchoolStrikeInfo info = null;
    School school = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("STRIKECENTRAL-PRINCIPAL-ADMINVIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    school = usr.getPersonnel().getSchool();
    if(school != null)
      request.setAttribute("School", school);
      
    group = SchoolStrikeGroupDB.getSchoolStrikeGroup(school);
    if(group != null)
      request.setAttribute("SchoolStrikeGroup", group);

    info = DailySchoolStrikeInfoDB.getDailySchoolStrikeInfo(school);
    if(info != null)
      request.setAttribute("DailySchoolStrikeInfo", info);
    
    path = "principaladmin.jsp";
    
    return path;
  }
}