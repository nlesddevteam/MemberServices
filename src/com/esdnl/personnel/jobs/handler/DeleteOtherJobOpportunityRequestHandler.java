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

public class DeleteOtherJobOpportunityRequestHandler extends RequestHandlerImpl
{
	public DeleteOtherJobOpportunityRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("job_id", "Job Id required for deletion"),
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
				Integer job_id=Integer.parseInt(request.getParameter("job_id"));
				OtherJobOpportunityManager.deleteOtherJobOpportunityBean(job_id);
				request.setAttribute("msg", "Job opportunity successfully deleted.");
				path = "admin_view_job_posts_other.jsp?";
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not delete Job Opportunity.");
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}
		return path;
	}
}

