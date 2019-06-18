package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.ceoweb.bean.*;
import com.esdnl.webmaint.ceoweb.dao.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class ArchiveMessageRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {      
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
           && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DIRECTORSWEB")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    try
    {
            if(request.getParameter("id") == null)
            {
              request.setAttribute("edit_msg", "MESSAGE ID IS REQUIRED");
            }
            else
            {
                  try
                  { 
                    
                      MessageManager.archiveMessageBean(Integer.parseInt(request.getParameter("id")));
                        
                      request.setAttribute("edit_msg", "MESSAGE ARCHIVED SUCCESSFULLY");
                  }
                  catch(CeoWebException e)
                  {
                    switch(((SQLException)e.getCause()).getErrorCode())
                    {
                      case 1:
                        request.setAttribute("edit_msg", "A REPORT FOR  ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("edit_msg", e.getMessage());
                    }
                  }
              }              
            
      
      request.setAttribute("MESSAGES", MessageManager.getMessageBeans(Integer.parseInt(request.getParameter("id")), false));
      request.setAttribute("ARCHIVED_MESSAGES",  MessageManager.getMessageBeans(Integer.parseInt(request.getParameter("id")), true));
      request.setAttribute("VIEW_TYPE", new Integer(Integer.parseInt(request.getParameter("id"))));
    }
    catch(Exception e)
    {
      request.setAttribute("edit_msg", e.getMessage());
    }
   
    path = "messages.jsp";
    
    return path;
  }
}