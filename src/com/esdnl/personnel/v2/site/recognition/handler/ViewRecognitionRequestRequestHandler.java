package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.database.recognition.*;
import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.model.recognition.constant.*;

public class ViewRecognitionRequestRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-RECOGNITION-VIEW")
        || usr.getUserPermissions().containsKey("PERSONNEL-RECOGNITION-ADMIN-VIEW")))
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
      String id = request.getParameter("id");
      
      request.setAttribute("RECOGNITION_REQUEST", RecognitionRequestManager.getRecognitionRequestBean(Integer.parseInt(id)));
      
      path = "view_request.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "index.jsp";
    }
    
    return path;
  }
}