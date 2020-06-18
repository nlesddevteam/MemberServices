package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteApplicantReferenceRequestHandler extends PersonnelApplicationRequestHandlerImpl 
{
	public DeleteApplicantReferenceRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("del", "ID required for deletion")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String path;
		if (validate_form()) {
			try {
				String id = request.getParameter("del");
				//delete the supervisor
				ApplicantSupervisorManager.deleteApplicantSupervisorBean(Integer.parseInt(id));
				//delete any linked ref requests
				ApplicantRefRequestManager.deleteReferenceRequestBySupervisor(Integer.parseInt(id));
				request.setAttribute("msg", "Reference successfully deleted.");
				path = "applicant_registration_step_8.jsp";
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not delete reference.");
				path = "applicant_registration_step_8.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_8.jsp";
		}
		return path;
	}
}