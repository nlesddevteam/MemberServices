package com.esdnl.webmaint.minutes.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.minutes.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class DeleteMinutesRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    File f = null;
    String path = "";
    Iterator iter = null;
    SimpleDateFormat sdf = null;
    java.util.Date dt = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {      
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
           && usr.getUserPermissions().containsKey("WEBMAINTENANCE-BOARDMINUTES")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if((request.getParameter("dt") == null) || request.getParameter("dt").equals(""))
    {
      request.setAttribute("edit_msg", "MEETING DATE IS REQUIRED");
    }
    else
    {
      try
      {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        dt = sdf.parse(request.getParameter("dt").trim());
        
        if(BoardMeetingMinutesDB.deleteMinutes(dt))
        {
          f = new File(session.getServletContext().getRealPath("/") + "../ROOT/board/minutes/archive/esdnl/"
                + new SimpleDateFormat("dd_MM_yyyy").format(dt) + ".pdf");
        
          if(f.exists())
            f.delete();
        }
          
        request.setAttribute("edit_msg", "MEETING MINUTES DELETED SUCCESSFULLY");
      }
      catch(SQLException e)
      {
        switch(e.getErrorCode())
        {
          case 1:
            request.setAttribute("edit_msg", "CATEGORY " + request.getParameter("cat_code") + " ALREADY EXISTS");
            break;
          default:
            request.setAttribute("edit_msg", e.getMessage());
        }
      }
      catch(ParseException e)
      {
        request.setAttribute("edit_msg", e.getMessage());
      }
    }
   
    request.setAttribute("MINUTES", BoardMeetingMinutesDB.getMinutes());
    path = "minutes.jsp";
    
    return path;
  }
}