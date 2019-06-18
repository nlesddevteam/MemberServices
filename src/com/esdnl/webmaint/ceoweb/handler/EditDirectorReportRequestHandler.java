package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
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

public class EditDirectorReportRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    SimpleDateFormat sdf_file = new SimpleDateFormat("MM_yyyy");
    SimpleDateFormat sdf_full = new SimpleDateFormat("MMMM yyyy");
    Date rpt_dt = null;
    DirectorReportBean rpt = null;
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
        
            if(request.getParameter("dt") == null)
            {
              request.setAttribute("edit_msg", "REPORT DATE IS REQUIRED");
            }
            else
            {
                  try
                  { 
                    rpt_dt = sdf_file.parse(request.getParameter("dt"));
                    //Calendar cal = Calendar.getInstance();
                    //cal.setTime(rpt_dt);
                    //cal.set(Calendar.DATE, 1);
                    
                    //System.out.println(sdf_file.format(rpt_dt) + "_title");
                    
                    if(mrequest.getParameter(sdf_file.format(rpt_dt) + "_title") == null)
                    {
                      request.setAttribute("edit_msg", "REPORT TITLE IS A REQUIRED FIELD.");
                    }
                    else
                    {
                      rpt = DirectorReportManager.getDirectorReportBean(rpt_dt);
                      
                      //System.err.println("rpt: " + (rpt == null));
                      
                      if(rpt == null)
                      {
                        request.setAttribute("edit_msg", "REPORT COULD NOT BE FOUND IN DATABASE.");  
                      }
                      else
                      {
                        rpt.setReportTitle(mrequest.getParameter(sdf_file.format(rpt_dt) + "_title").trim());
                        DirectorReportManager.updateDirectorReportBean(rpt);
                        request.setAttribute("edit_msg", "REPORT UPDATED SUCCESSFULLY");  
                        
                        //System.err.println("rpt updated. ");
                      }
                    }
                  }
                  catch(CeoWebException e)
                  {
                    switch(((SQLException)e.getCause()).getErrorCode())
                    {
                      case 1:
                        request.setAttribute("edit_msg", "A REPORT FOR " + sdf_full.format(rpt_dt) + " ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("edit_msg", e.getMessage());
                    }
                  }
              }              
      } 
      
      request.setAttribute("DIRECTOR-REPORTS", DirectorReportManager.getDirectorReportBeans());
      //System.err.println("rpts refreshed.");
    }
    catch(Exception e)
    {
      request.setAttribute("edit_msg", e.getMessage());
    }
   
    path = "director_reports.jsp";
    
    return path;
  }
}