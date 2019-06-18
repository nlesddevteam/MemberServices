package com.esdnl.personnel.jobs.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.personnel.jobs.constants.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AwardJobOpportunityRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")))
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
      String comp_num = request.getParameter("comp_num");
      
      if(StringUtils.isEmpty(comp_num))
      {
        request.setAttribute("msg", "Competition number is a required field.");
        path = "admin_view_job_posts.jsp?status=Open";
      }
      else if(request.getParameter("confirmed") == null)
      {
        request.setAttribute("op", "AWARD");
        request.setAttribute("COMP_NUM", comp_num); 
        path = "confirm.jsp";
      }
      else
      {
        JobOpportunityManager.awardJobOpportunityBean(comp_num);
        
        request.setAttribute("msg", "Job " + comp_num + " awarded successfully.");
        path = "view_job_post.jsp?comp_num=" + comp_num;
        
      }
    } 
    catch(JobOpportunityException e)
    {
      request.setAttribute("msg", "Could not post job.");
      path = "admin_view_job_posts.jsp?status=Open";
    }
    

    return path;
  }
}