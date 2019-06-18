package com.esdnl.personnel.jobs.handler;

import com.awsd.servlet.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewReferenceChecklistRequestHandler implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = "teacher_reference_checklist.jsp";
    
    try
    {
    	String id = request.getParameter("id");
    	
    	if(!StringUtils.isEmpty(id)){
    		ReferenceCheckRequestBean bean  = ReferenceCheckRequestManager.getReferenceCheckRequestBean(Integer.parseInt(id));
    		
    		if(bean != null){
    			request.setAttribute("REFERENCE_CHECK_REQUEST_BEAN", bean);
    			
    			AdRequestBean ad = AdRequestManager.getAdRequestBean(bean.getCompetitionNumber());
    			request.setAttribute("AD_REQUEST_BEAN", ad);
    			
    			JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(bean.getCompetitionNumber());
    			request.setAttribute("JOB", job);
    			
    			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
    			request.setAttribute("JOB_ASSIGNMENTS", ass);
    			
    			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(bean.getCandidateId());
    			request.setAttribute("PROFILE", profile);
    			
    			if(bean.getReferenceId() > 0){
    				request.setAttribute("REFERENCE_BEAN", ReferenceManager.getReferenceBean(bean.getReferenceId()));
    				path = "view_teacher_reference.jsp";
    			}
    		}
    	}
    	else
    		request.setAttribute("msg", "Reference check request information could not be found.");
    		
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
    }
    
    return path;
  }
}