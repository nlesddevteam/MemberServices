package com.esdnl.personnel.jobs.handler;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteApplicantESDReplExperienceRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public DeleteApplicantESDReplExperienceRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("del", "ID required for deletion")
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
				String id = request.getParameter("del");
				ApplicantEsdReplExpManager.deleteApplicantEsdReplacementExperienceBean(Integer.parseInt(id));
				request.setAttribute("msg", "ESD replacement experience successfully deleted.");
				path = "applicant_registration_step_2_repl_exp.jsp";
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not delete ESD replacement experience.");
				path = "applicant_registration_step_2_repl_exp.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_2_repl_exp.jsp";
		}


		return path;
	}
}