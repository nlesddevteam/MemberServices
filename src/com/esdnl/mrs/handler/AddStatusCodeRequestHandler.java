package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddStatusCodeRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String username = "";
    String hash = "";
    Vector types = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if(request.getParameter("op") != null)
    {
      if(request.getParameter("op").equalsIgnoreCase("ADD"))
      {
        if(request.getParameter("code_id") != null)
        {
          StatusCodeDB.addStatusCode(new StatusCode(request.getParameter("code_id")));
          request.setAttribute("msg", "STATUS CODE added successfully.");
        }
        else
        {
          request.setAttribute("msg", "STATUS CODE ID is required.");
        }
        
        request.setAttribute("STATUS_CODES", StatusCodeDB.getStatusCodes());
        path = "add_status_code.jsp";
      }
      else if(request.getParameter("op").equalsIgnoreCase("DEL"))
      {
        if(StatusCodeDB.deleteStatusCode(new StatusCode(request.getParameter("t_id"))))
        {
          request.setAttribute("msg", "STATUS CODE deleted successfully.");
        }
        else
        {
          request.setAttribute("msg", "STATUS CODE not deleted!");
        }
        request.setAttribute("STATUS_CODES", StatusCodeDB.getStatusCodes());
        path = "add_status_code.jsp";
      }
      else
      {
        path = "error_message.jsp";
        request.setAttribute("msg", "INVALID OPTION");
      }
    }
    else
    {
      request.setAttribute("STATUS_CODES", StatusCodeDB.getStatusCodes());
      path = "add_status_code.jsp";
    }
    
    return path;
  }
}