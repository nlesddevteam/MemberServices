package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;

public class AddEntityRequestHandler implements RequestHandler
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
        String location = request.getParameter("location");
        String[] emps = request.getParameterValues("emps");
        
        if(op.equals("GET_EMPLOYEES"))
        {
          if(StringUtils.isEmpty(location))
          {
            request.setAttribute("msg", "Please select LOCATION.");
          }
          else
          {
            request.setAttribute("LOCATION", location);
          }
        }
        else if(op.equals("ADD_EMPLOYEES"))
        {
          if((emps == null) || (emps.length <= 0))
          {
            request.setAttribute("msg", "Please select Employee(s) to add.");
          }
          else
          {
            RecognitionRequestBean rbean = null;
            
            if(session.getAttribute("RECOGNITION_REQUEST") != null)
              rbean = (RecognitionRequestBean) session.getAttribute("RECOGNITION_REQUEST");
            else
              rbean = new RecognitionRequestBean();
              
            for(int i=0; i < emps.length; i++)
              rbean.addEntity(EmployeeManager.getEmployeeBean(emps[i]));
            
              session.setAttribute("RECOGNITION_REQUEST", rbean);
              request.setAttribute("msg", "Employee(s) added successfully.");
              request.setAttribute("REFRESH_REQUEST", new Boolean(true)); 
          }
        }
        path = "add_entity.jsp";
      }
      else
        path = "add_entity.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "add_entity.jsp";
    }
    
    return path;
  }
}