package com.esdnl.roer.handler;

import com.awsd.personnel.profile.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.roer.*;

import java.io.*;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewROERequestRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    Vector roers = null;
    String path = "";
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {      
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("ROEREQUEST-VIEW")
        || usr.getUserPermissions().containsKey("ROEREQUEST-ADMIN")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
   
    if(usr.getUserPermissions().containsKey("ROEREQUEST-ADMIN"))
    {
      try
      {
        if((request.getParameter("op")!=null) && request.getParameter("op").equals("VIEW") && (request.getParameter("rid")!=null))
        {
          ROERequest roer = ROERequestDB.getROERequest(Integer.parseInt(request.getParameter("rid")));
          request.setAttribute("CURRENT_PROFILE", roer.getPersonnel().getProfile());
          request.setAttribute("ROER", roer);
          path = "index.jsp";  
        }
        else
        {
          request.setAttribute("OUTSTANDING_REQUESTS", ROERequestDB.getROERequests());
          path = "requests.jsp";  
        }
      }
      catch(SQLException e)
      {
        e.printStackTrace(System.err);
        path = "requests.jsp"; 
      }
    }
    else
    {
      try
      {
        if((request.getParameter("op")!=null) && request.getParameter("op").equals("VIEW") && (request.getParameter("rid")!=null))
        {
          ROERequest roer = ROERequestDB.getROERequest(Integer.parseInt(request.getParameter("rid")));
          request.setAttribute("CURRENT_PROFILE", roer.getPersonnel().getProfile());
          request.setAttribute("ROER", roer);
          path = "index.jsp";  
        }
        else if((request.getParameter("op")!=null) && request.getParameter("op").equals("DELETE") && (request.getParameter("rid")!=null))
        {
          ROERequestDB.deleteROERequest(Integer.parseInt(request.getParameter("rid")));
          
          roers = ROERequestDB.getROERequests(usr.getPersonnel());
          if((roers != null) && (roers.size() > 0))
          {
            request.setAttribute("OUTSTANDING_REQUESTS", roers);
            path = "emp_requests.jsp";
          }
          else
          {
            request.setAttribute("CURRENT_PROFILE", ProfileDB.getProfile(usr.getPersonnel()));
            path = "index.jsp";
          }
        }
        else
        {
          if((request.getParameter("op")!=null) && request.getParameter("op").equals("NEW"))
          {
            request.setAttribute("CURRENT_PROFILE", ProfileDB.getProfile(usr.getPersonnel()));
            path = "index.jsp";
          }
          else
          {
            roers = ROERequestDB.getROERequests(usr.getPersonnel());
            if((roers != null) && (roers.size() > 0))
            {
              request.setAttribute("OUTSTANDING_REQUESTS", roers);
              path = "emp_requests.jsp";
            }
            else
            {
              request.setAttribute("CURRENT_PROFILE", ProfileDB.getProfile(usr.getPersonnel()));
              path = "index.jsp";
            }
          }
        }
      }
      catch(SQLException e)
      {
        e.printStackTrace(System.err);
        request.setAttribute("CURRENT_PROFILE", ProfileDB.getProfile(usr.getPersonnel()));
        path = "index.jsp";
      }
    }

    return path;
  }
}