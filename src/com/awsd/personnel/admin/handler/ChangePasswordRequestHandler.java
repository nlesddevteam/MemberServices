package com.awsd.personnel.admin.handler;

import com.awsd.mail.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.mail.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ChangePasswordRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String pwd = "";
    SMTPAuthenticatedMail smtp = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("USER-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    if((request.getParameter("op") != null) && request.getParameter("op").equals("CONFIRMED"))
    {
      if(request.getParameter("password") == null)
      {
        request.setAttribute("msg", "New password required.");
      }
      else if(request.getParameter("confirm") == null)
      {
        request.setAttribute("msg", "New Password confirmation required.");
      }
      else if(!request.getParameter("password").equals(request.getParameter("confirm")))
      {
        request.setAttribute("msg", "Confirmed password does not match. Please try again.");
      }
      else
      {
        pwd = request.getParameter("password");
                
        try
        {
          smtp = new SMTPAuthenticatedMail("mail.esdnl.ca", "admin", "2ndclass");
        
          smtp.postMail(new String[]{"batch@esdnl.ca", "chriscrane@esdnl.ca"}, null, null, "Change Password", 
                          "PW First2004<br>REPLY<br>PUT USER " + usr.getUsername() + " 1217 0 " + pwd,
                          "chriscrane@esdnl.ca");
        
          request.setAttribute("msg", "Password has been changed.");
        }
        catch(MessagingException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not change password. Please try again.");
        }
      }
    }
    
    path = "changePassword.jsp";

    return path;
  }
}