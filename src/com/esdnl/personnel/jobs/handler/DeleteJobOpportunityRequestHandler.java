package com.esdnl.personnel.jobs.handler;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteJobOpportunityRequestHandler extends RequestHandlerImpl
{
	public DeleteJobOpportunityRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num", "COMP_NUM required for deletion"),
				new RequiredFormElement("confirmed", "Could not delete job post")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String path;
		if (validate_form()) {
			try
			{
				String comp_num = request.getParameter("comp_num");
				JobOpportunityBean jb = JobOpportunityManager.getJobOpportunityBean(comp_num); 
				JobOpportunityManager.deleteJobOpportunityBean(comp_num);

				request.setAttribute("msg", "Job opportunity successfully deleted.");
				if(jb.getIsSupport().equals("N")){
					path = "admin_view_job_posts.jsp?status=Open";
				}else{
					path = "admin_view_job_posts_other.jsp?status=Open&zoneid=0";
				}
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not delete applicant education.");
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}


		return path;
	}
}