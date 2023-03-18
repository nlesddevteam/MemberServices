package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class UncancelJobOpportunityRequestHandler extends RequestHandlerImpl {
	public UncancelJobOpportunityRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num", "COMP_NUM required for cancellation")
		});
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-UNCANCEL"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String path;
		super.handleRequest(request, response);
		if (validate_form()) {
			try
			{
				String comp_num = request.getParameter("comp_num");
				JobOpportunityManager.uncancelJobOpp(comp_num); 
				request.setAttribute("msg", "SUCCESS: Job opportunity successfully uncancelled.");
				path = "view_job_post.jsp?comp_num=" + comp_num;
				
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not uncancel applicant education.");
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}


		return path;
	}
}