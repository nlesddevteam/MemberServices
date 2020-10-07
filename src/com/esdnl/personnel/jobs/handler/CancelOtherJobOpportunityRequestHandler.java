package com.esdnl.personnel.jobs.handler;
import com.awsd.security.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelOtherJobOpportunityRequestHandler extends RequestHandlerImpl {
	public CancelOtherJobOpportunityRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("job_id", "Job ID required for cancellation"),
				new RequiredFormElement("confirmed", "Could not cancel this job post")
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
				Integer job_id=Integer.parseInt(request.getParameter("job_id"));
				usr = (User) session.getAttribute("usr");
				OtherJobOpportunityManager.cancelOtherJobOpportunityBean(job_id,request.getParameter("textReason"),usr.getUsername());
				request.setAttribute("msg", "Job opportunity successfully cancelled.");
				path = "admin_view_job_posts_other.jsp?";


			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not cancel Job Opportunity.");
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}
		return path;
	}
}
