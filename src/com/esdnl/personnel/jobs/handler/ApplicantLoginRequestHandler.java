package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.util.StringUtils;

public class ApplicantLoginRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		ApplicantProfileBean profile = null;

		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			if (StringUtils.isEmpty(email)) {
				request.setAttribute("errmsg", "Please specify your email address.");
				path = "applicant_login.jsp";
			}
			else if (StringUtils.isEmpty(password)) {
				request.setAttribute("errmsg", "Please specify your password.");
				path = "applicant_login.jsp";
			}
			else {
				profile = ApplicantProfileManager.getApplicantProfileBeanByEmail(email.toLowerCase());

				if ((profile == null) || !profile.getPassword().equals(password)) {
					request.setAttribute("errmsg", "Email address and/or password are incorrect.");
					path = "applicant_login.jsp";
				}
				else {
					request.setAttribute("AUTHENTICATED", new Boolean(true));
					request.getSession(true).setAttribute("APPLICANT", profile);
					ApplicantProfileManager.applicantLoggedIn(profile);
					request.setAttribute("msg", "Login Successful!");
					path = "applicant_login.jsp";
				}
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not find applicant record on file, please try again.");
			path = "applicant_login.jsp";
		}

		return path;
	}
}