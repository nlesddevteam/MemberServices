package com.awsd.efile.equestion.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewQuestionCartContentRequestHandler implements RequestHandler
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

    if(((HashMap)session.getAttribute("EQUESTION-CART"))== null)
      session.setAttribute("EQUESTION-CART", new HashMap(10));
    
    path = "questionCart_main.jsp?page=" + request.getParameter("page");
    
    return path;
  }
}