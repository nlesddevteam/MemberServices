package com.esdnl.personnel.v2.site.availability.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;

import com.awsd.servlet.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.database.availability.*;

public class EmployeeUnbookRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-SUBSTITUTES-STUDASS-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    try
    {
      SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
      
      String id = request.getParameter("id");
      String start_date = request.getParameter("start_date");
      String end_date = request.getParameter("end_date");
      
      EmployeeAvailabilityManager.deleteEmployeeAvailabilityBean(id, sdf2.parse(start_date), sdf2.parse(end_date));
       
      request.setAttribute("msg", "Employee availability status was changed successfully.");
      
      request.setAttribute("view_date", request.getParameter("view_date"));
      
      path = "availability_list.jsp";
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "availability_list.jsp";
    }
    
    return path;
  }
}