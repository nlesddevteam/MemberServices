package com.awsd.pdreg.handler;

import com.awsd.mail.bean.*;
import com.awsd.pdreg.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;



public class SendEventParticipantsEmailRequestHandler   implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    UserPermissions permissions = null;
    String msg = "";
    String to[]=null, cc[]=null, bcc[]=null;
    String subject="", message="";
    
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
    try
    {
      if(request.getParameter("to") != null)
      {
        to = (usr.getPersonnel().getEmailAddress()+ ";" +request.getParameter("to")).split(";");
        System.err.println("TO: " + request.getParameter("to"));
      }
      else
      {
        throw new EventException("To Field required to send email.");
      }

      if((request.getParameter("cc") != null) && (!request.getParameter("cc").equals("")))
      {
        cc = request.getParameter("cc").split(";");
        System.err.println("CC: " + request.getParameter("cc"));
      }
      else
      {
        cc = null;
      }

      if((request.getParameter("bcc") != null) && (!request.getParameter("bcc").equals("")))
      {
        bcc = request.getParameter("bcc").split(";");
        System.err.println("BCC: " + request.getParameter("bcc"));
      }
      else
      {
        bcc = null;
      }

      if(request.getParameter("subject") != null)
      {
        subject = request.getParameter("subject");
      }
    
      if(request.getParameter("message") != null)
      {
        message = request.getParameter("message");
      }
      else
      {
        throw new EventException("Email body is blank.");
      }
      
      EmailBean email = new EmailBean();
      email.setTo(to);
      email.setCC(cc);
      email.setBCC(bcc);
      email.setSubject(subject);
      email.setBody(message);
      email.setFrom(usr.getPersonnel().getEmailAddress());
      email.send();
      
      
      msg = "Email sent successfully.";
      
    }
    catch(Exception e)
    {
      System.err.println(e);
      e.printStackTrace(System.err);
      //throw new EventException("COULD NOT SEND PARTICIPANT EMAIL.");
      msg = "Could not send email.";
    }
    
    request.setAttribute("msg", msg);
    
    return "email_reply.jsp";
  }
}