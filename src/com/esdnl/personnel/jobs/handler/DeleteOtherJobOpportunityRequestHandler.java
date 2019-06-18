package com.esdnl.personnel.jobs.handler;
import com.awsd.security.*;
import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteOtherJobOpportunityRequestHandler implements LoginNotRequiredRequestHandler{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    try
	    {
	      String comp_num = request.getParameter("title");
	      Integer job_id=Integer.parseInt(request.getParameter("job_id"));
	      
	      if(StringUtils.isEmpty(comp_num))
	      {
	        request.setAttribute("msg", "Job Title required for deletion.");
	        path = "view_other_job_post.jsp";
	      }
	      else if(request.getParameter("confirmed") == null)
	      {
	        request.setAttribute("op", "DELETE");
	        request.setAttribute("COMP_NUM", comp_num); 
	        path = "confirm.jsp";
	      }
	      else
	      {
	        OtherJobOpportunityManager.deleteOtherJobOpportunityBean(job_id);
	        
	        request.setAttribute("msg", "Job opportunity successfully deleted.");
	        path = "admin_view_job_posts_other.jsp?";
	      }
	      
	    }
	    catch(JobOpportunityException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could not delete Job Opportunity.");
	      path = "admin_index.jsp";
	    }
	    
	    return path;
	  }
}

