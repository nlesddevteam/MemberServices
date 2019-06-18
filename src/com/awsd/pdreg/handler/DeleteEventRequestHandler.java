package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.pdreg.worker.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteEventRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Event evt = null;
    String path = "";
    int id;
    boolean deleted = false;
    RegisteredPersonnel registered = null;
    HttpSession session = null;
    User usr = null;
    UserPermissions permissions = null;
    
    try
    {
      session = request.getSession(false);
      if((session != null) && (session.getAttribute("usr") != null))
      {
        usr = (User) session.getAttribute("usr");
        permissions = usr.getUserPermissions();
        if(!(permissions.containsKey("CALENDAR-VIEW") 
          && permissions.containsKey("CALENDAR-SCHEDULE")))
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
      }
      else
      {
        throw new EventException("Event ID required for deletion.");
      }

      evt = EventDB.getEvent(id);

      if(request.getParameter("confirmed") != null)
      {
        registered = new RegisteredPersonnel(evt);

        deleted = EventDB.removeEvent(evt);
        if(deleted)
        {
          //new DeleteEventWorkerThread(evt, registered).start();
          new FirstClassWorkerThread(usr.getPersonnel(), new Event[]{evt}, FirstClassWorkerThread.DELETE_EVENT).start();
          
          request.setAttribute("msg", "Event deleted successfully.");
        }
        else
        {
          request.setAttribute("msg", "Event deleted successfully.");
        }
      }
      else
      {
        request.setAttribute("evt", evt);
      }
      
      path = "deleteevent.jsp";
    }
    catch(NumberFormatException e)
    {
      path = "error.jsp";
      throw new EventException("Could not parse Event ID.\n" + e);
    }
    catch(EventException e)
    {
      path = "error.jsp";
      throw e;
    }
    
    return path;
  }
}