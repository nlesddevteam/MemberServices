package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DistrictCloseOutRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    UserPermissions permissions = null;
    CloseoutEvents events = null;
    Event evt = null;
    int id;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      permissions = usr.getUserPermissions();
      if(!permissions.containsKey("CALENDAR-VIEW"))
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
      evt = EventDB.getEvent(id);
    }
    else
    {
      throw new EventException("District Closeout Event ID required.");
    }
   
    events = new CloseoutEvents(evt, new String[]{"A", "B", "C"});

    request.setAttribute("events", events);

    return "closeout.jsp";
  }
}