package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;
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

public class EditSchoolVisitRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    SimpleDateFormat sdf_cal = new SimpleDateFormat("dd/MM/yyyy");
    SchoolVisitBean visit = null;
    MultipartFormDataRequest mrequest = null;
    
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
      if(MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);
        
            if(request.getParameter("id") == null)
            {
              request.setAttribute("edit_msg", "VISIT_ID IS REQUIRED");
            }
            else
            {
                  try
                  { 
                    if(mrequest.getParameter("visit_date_" + request.getParameter("id")) == null)
                    {
                      request.setAttribute("edit_msg", "VISIT DATE IS A REQUIRED FIELD.");
                    }
                    else if(mrequest.getParameter("caption_" + request.getParameter("id")) == null)
                    {
                      request.setAttribute("edit_msg", "CAPTION IS A REQUIRED FIELD.");
                    }
                    else
                    {
                      visit = SchoolVisitManager.getSchoolVisitBean(Integer.parseInt(request.getParameter("id")));
                      
                      //System.err.println("rpt: " + (rpt == null));
                      
                      if(visit == null)
                      {
                        request.setAttribute("edit_msg", "SCHOOL VISIT COULD NOT BE FOUND IN DATABASE.");  
                      }
                      else
                      {
                        visit.setVisitDate(sdf_cal.parse(mrequest.getParameter("visit_date_" + request.getParameter("id"))));
                        visit.setCaption(mrequest.getParameter("caption_" + request.getParameter("id")).trim());
                        SchoolVisitManager.updateSchoolVisitBean(visit);
                        request.setAttribute("edit_msg", "SCHOOL VISIT UPDATED SUCCESSFULLY");  
                      }
                    }
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
      } 
      
      request.setAttribute("SCHOOL_VISITS", SchoolVisitManager.getSchoolVisitBeans(false));
      request.setAttribute("ARCHIVED_SCHOOL_VISITS", SchoolVisitManager.getSchoolVisitBeans(true));
    }
    catch(Exception e)
    {
      request.setAttribute("edit_msg", e.getMessage());
    }
   
    path = "school_visits.jsp";
    
    return path;
  }
}