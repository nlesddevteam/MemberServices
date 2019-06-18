package com.esdnl.personnel.jobs.handler;
import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class DeleteApplicantNLESDExperienceSSRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    try
	    {
	      
	      String id = request.getParameter("del");
	     
	      if(StringUtils.isEmpty(id))
	      {
	        request.setAttribute("msg", "ID required for deletion.");
	        path = "applicant_registration_step_2_ss.jsp";
	      }
	      else
	      {
	    	ApplicantCurrentPositionManager.deleteApplicantCurrentPositionBean(Integer.parseInt(id));
			request.setAttribute("msg", "Current Position successfully deleted.");
	        path = "applicant_registration_step_2_ss.jsp";
	      }
	      
	    }
	    catch(JobOpportunityException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could not delete current position.");
	      path = "applicant_registration_step_2_ss.jsp";
	    }
	    
	    return path;
	  }
	}
