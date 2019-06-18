package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.User;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
import com.esdnl.util.StringUtils;

public class DeleteApplicantReferenceRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;

		try {

			String id = request.getParameter("del");

			if (StringUtils.isEmpty(id)) {
				request.setAttribute("msg", "ID required for deletion.");
				path = "applicant_registration_step_8.jsp";
			}
			else {
				ApplicantSupervisorManager.deleteApplicantSupervisorBean(Integer.parseInt(id));

				request.setAttribute("msg", "Reference successfully deleted.");
				path = "applicant_registration_step_8.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not reference.");
			path = "applicant_registration_step_8.jsp";
		}

		return path;
	}
}