package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewMemberScheduledEventsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    ScheduledEvents evts = null;
    Personnel p = null;
    int pid;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("CALENDAR-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    if(request.getParameter("pid") != null)
      {
        pid = Integer.parseInt((String)request.getParameter("pid"));
        if(pid > 0)
        {
          p = PersonnelDB.getPersonnel(pid);
        }
      }
      else
      {
        throw new EventException("Personnel ID required.");
      }
   
    if(p != null)
    {
      evts = new ScheduledEvents(p);
    }
    else
    {
      evts = new ScheduledEvents(pid);
    }
    
    request.setAttribute("ScheduledEvents", evts);
  
    return "scheduledevents.jsp";
  }
}