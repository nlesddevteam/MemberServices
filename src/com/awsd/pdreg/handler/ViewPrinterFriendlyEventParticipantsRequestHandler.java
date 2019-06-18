package com.awsd.pdreg.handler;

import com.awsd.pdreg.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewPrinterFriendlyEventParticipantsRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    UserPermissions permissions = null;
    RegisteredPersonnelCollection users = null;
    Event evt = null;
    int id;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      permissions = usr.getUserPermissions();
      if(!(permissions.containsKey("CALENDAR-VIEW") 
        && (permissions.containsKey("CALENDAR-SCHEDULE")
            || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))))
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
        throw new EventException("Event ID required for registration.");
      }
   

    users = new RegisteredPersonnelCollection(evt, RegisteredPersonnelCollection.SORT_BY_SCHOOL);
    request.setAttribute("RegisteredPersonnel", users);

    return "printeventparticipants.jsp";
  }
}