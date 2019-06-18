package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantNLESDPermExpManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class DeleteApplicantNLESDPermExperienceRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
	IOException {
		super.handleRequest(request, response);
	    String path;
	    try
	    {
	      String id = form.get("del");
	      if(StringUtils.isEmpty(id))
	      {
	        request.setAttribute("msg", "ID required for deletion.");
	        path = "applicant_registration_step_2_perm_exp.jsp";
	      }
	      else
	      {
	        ApplicantNLESDPermExpManager.deleteApplicantNLESDPermanentExperienceBean(Integer.parseInt(id));
	        request.setAttribute("msg", "ESD permanent experience successfully deleted.");
	        path = "applicant_registration_step_2_perm_exp.jsp";
	      }
	      
	    }
	    catch(JobOpportunityException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could not delete NLESD permanent experience.");
	      path = "applicant_registration_step_2_perm_exp.jsp";
	    }
	    return path;
	  }
}
