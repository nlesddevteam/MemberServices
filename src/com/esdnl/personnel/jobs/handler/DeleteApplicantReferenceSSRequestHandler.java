package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
import com.esdnl.util.StringUtils;
public class DeleteApplicantReferenceSSRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		try {

			String id = request.getParameter("del");

			if (StringUtils.isEmpty(id)) {
				request.setAttribute("msg", "ID required for deletion.");
				path = "applicant_registration_step_8_ss.jsp";
			}
			else {
				ApplicantSupervisorManager.deleteApplicantSupervisorBean(Integer.parseInt(id));

				request.setAttribute("msg", "Reference successfully deleted.");
				path = "applicant_registration_step_8_ss.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not reference.");
			path = "applicant_registration_step_8_ss.jsp";
		}

		return path;
	}
}
