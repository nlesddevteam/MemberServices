package com.esdnl.personnel.jobs.handler;

import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteJobOpportunityRequestHandler implements LoginNotRequiredRequestHandler
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
        request.setAttribute("msg", "COMP_NUM required for deletion.");
        path = "view_job_post.jsp";
      }
      else if(request.getParameter("confirmed") == null)
      {
        //request.setAttribute("op", "DELETE");
        //request.setAttribute("COMP_NUM", comp_num); .
    	  request.setAttribute("msg", "ERROR: Could not delete job post.");
        path = "view_job_post.jsp";
      }
      else
      {
    	 JobOpportunityBean jb = JobOpportunityManager.getJobOpportunityBean(comp_num); 
        JobOpportunityManager.deleteJobOpportunityBean(comp_num);
        
        request.setAttribute("msg", "Job opportunity successfully deleted.");
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
      request.setAttribute("msg", "Could not delete applicant education.");
      path = "admin_index.jsp";
    }
    
    return path;
  }
}