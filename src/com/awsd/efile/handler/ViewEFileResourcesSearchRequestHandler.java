package com.awsd.efile.handler;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

import com.awsd.servlet.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;

public class ViewEFileResourcesSearchRequestHandler  implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    path = "searchRepository.jsp";
    
    return path;
  }
}