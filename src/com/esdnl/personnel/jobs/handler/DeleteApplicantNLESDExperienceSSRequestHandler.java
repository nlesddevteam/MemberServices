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
public class DeleteApplicantNLESDExperienceSSRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public DeleteApplicantNLESDExperienceSSRequestHandler() {
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
				ApplicantCurrentPositionManager.deleteApplicantCurrentPositionBean(Integer.parseInt(id));
				request.setAttribute("msg", "Current Position successfully deleted.");
				path = "applicant_registration_step_2_ss.jsp";
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not delete current position.");
				path = "applicant_registration_step_2_ss.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_2_ss.jsp";
		}
		return path;
	}
}
