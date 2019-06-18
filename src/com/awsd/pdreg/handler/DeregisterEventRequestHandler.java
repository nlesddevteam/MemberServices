package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.pdreg.worker.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeregisterEventRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Event evt = null;
    String path = "";
    int id, pid=-1;
    HttpSession session = null;
    User usr = null;
    boolean deregistered = false;
    Personnel p = null;

    try
    {
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

      if(request.getParameter("id") != null)
      {
        id = Integer.parseInt((String)request.getParameter("id"));
        evt = EventDB.getEvent(id);
      }
      else
      {
        throw new EventException("Event ID required for deregistration.");
      }

      if((request.getParameter("confirmed") != null) || (request.getParameter("pid") != null))
      {
        //deregister event for user
        if(request.getParameter("pid") != null)
        {
          pid = Integer.parseInt(request.getParameter("pid"));
          p = PersonnelDB.getPersonnel(pid);  
        }
        else
        {
          p = usr.getPersonnel();
        }
        
        deregistered = EventDB.deregisterEvent(evt, p);

        if(deregistered)
        {
          //new DeregisterEventWorkerThread(p, evt).start();
          new FirstClassWorkerThread(p, new Event[]{evt}, FirstClassWorkerThread.DEREGISTER_EVENT).start();
          
          request.setAttribute("msg", "Deregistration successful.");
        }
        else
        {
          request.setAttribute("msg", "Deregistration unsuccessful. Please Try again.");
        }
      }
      
      request.setAttribute("evt", evt);
      if((p!=null) && (p.getPersonnelID() != usr.getPersonnel().getPersonnelID()))
      {
       path = "viewEventParticipants.html?id="+evt.getEventID();
      }
      else
      {
        path = "deregisterevent.jsp";  
      }
      
    }
    catch(NumberFormatException e)
    {
      throw new EventException("Could not parse Event ID.\n" + e);
    }
    
    return path;
  }
}