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

public class AddRecognitionRequestHandler  implements RequestHandler
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
      String op = request.getParameter("op");
      if(!StringUtils.isEmpty(op))
      {
        String request_class = request.getParameter("request_class");
        String desc = request.getParameter("desc");
        
        if(op.equals("PRESUBMIT"))
        {
          RecognitionRequestBean rbean = (RecognitionRequestBean)session.getAttribute("RECOGNITION_REQUEST");
          if(rbean == null)
            rbean = new RecognitionRequestBean();
          
          if(!StringUtils.isEmpty(request_class))
            rbean.setType(RequestType.get(Integer.parseInt(request_class)));
          else
            rbean.setType(null);
          
          if(!StringUtils.isEmpty(desc))
            rbean.setDescription(desc);
          else
            rbean.setDescription(null);
          
          session.setAttribute("RECOGNITION_REQUEST", rbean);
        }
        else if(op.equals("SUBMIT"))
        {
          RecognitionRequestBean rbean = (RecognitionRequestBean)session.getAttribute("RECOGNITION_REQUEST");
          if(rbean == null)
            rbean = new RecognitionRequestBean();
            
          String msg = null;
          
          if(StringUtils.isEmpty(request_class) || request_class.equals("-1"))
            msg = "Please select REQUEST CLASS.";
          else
            rbean.setType(RequestType.get(Integer.parseInt(request_class)));
          
          if((rbean.getEntities() == null) || (rbean.getEntities().length <= 0))
            msg = "Please add EMPLOYEE(S)/STUDENT(S).";
          
          if(StringUtils.isEmpty(desc))
            msg = "Please enter REQUEST DESCRIPTION.";
          else
            rbean.setDescription(desc);
          
          if(StringUtils.isEmpty(msg))
          {
            RecognitionRequestManager.addRecognitionRequestBean(rbean, usr.getPersonnel());
            session.setAttribute("RECOGNITION_REQUEST", null);
            msg = "Request submitted successfully.";
          }
          else
            session.setAttribute("RECOGNITION_REQUEST", rbean);
          
          request.setAttribute("msg", msg);
        }
        path = "add_request.jsp";
      }
      else
      {
        session.setAttribute("RECOGNITION_REQUEST", null);
        path = "add_request.jsp";
      }
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "add_request.jsp";
    }
    
    return path;
  }
}