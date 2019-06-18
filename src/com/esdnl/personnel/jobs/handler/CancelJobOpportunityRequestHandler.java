package com.esdnl.personnel.jobs.handler;

import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelJobOpportunityRequestHandler implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    
    try
    {
      String comp_num = request.getParameter("comp_num");
      
      if(StringUtils.isEmpty(comp_num))
      {
        request.setAttribute("msg", "COMP_NUM required for cancellation.");
        path = "view_job_post.jsp";
      }
      else if(request.getParameter("confirmed") == null)
      {
        //request.setAttribute("op", "CANCEL");
       // request.setAttribute("COMP_NUM", comp_num); 
    	  request.setAttribute("msg", "ERROR: Could not cancel this job post.");
        path = "view_job_post.jsp";
      }
      else
      {
    	JobOpportunityBean jb = JobOpportunityManager.getJobOpportunityBean(comp_num);  
        JobOpportunityManager.cancelJobOpportunityBean(comp_num);
        
        request.setAttribute("msg", "SUCCESS: Job opportunity successfully cancelled.");
        if(jb.getIsSupport().equals("N")){
        	path = "admin_view_job_posts.jsp?status=Open";
        }else{
        	path = "admin_view_job_posts_other.jsp?status=Open&zoneid=0";
        }
        
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("msg", "Could not cancel applicant education.");
      path = "admin_index.jsp";
    }
    
    return path;
  }
}