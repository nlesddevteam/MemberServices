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

public class ViewRecognitionRequestsRequestHandler implements RequestHandler
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
      RequestStatus status = RequestStatus.get(Integer.parseInt(request.getParameter("status")));
      
      request.setAttribute("REQUESTS", RecognitionRequestManager.getRecognitionRequestBean(status));
      
      if(status.equals(RequestStatus.SUBMITTED))
        request.setAttribute("TITLE", "PENDING APPROVAL");
      else if(status.equals(RequestStatus.APPROVED))
        request.setAttribute("TITLE", "PENDING PROCESS");
      
      path = "list_requests.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "index.jsp";
    }
    
    return path;
  }
}