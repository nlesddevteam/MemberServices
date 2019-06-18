package com.esdnl.personnel.jobs.handler;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.*;
import com.esdnl.util.*;

import com.esdnl.personnel.jobs.dao.*;

public class ManageJobPostsRequestHandler  extends RequestHandlerImpl
{
  public ManageJobPostsRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_view_job_posts.jsp?status="+request.getParameter("status");
    
    if(StringUtils.isEqual(form.get("op"), "AWARD"))
    {
      
      if(validate_form())
      {        
        try
        {
          String comp_num[] = request.getParameterValues("comp_num");
          
          for(int i=0; i < comp_num.length; i++){
          	JobOpportunityManager.awardJobOpportunityBean(comp_num[i]);
          }
          
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not award job opprtunity.");
          request.setAttribute("FORM", form);
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    }
    
    return path;
  }
}