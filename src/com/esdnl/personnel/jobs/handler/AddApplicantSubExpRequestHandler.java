package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubstituteTeachingExpBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantSubExpManager;
import com.esdnl.util.StringUtils;

public class AddApplicantSubExpRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;

		try {
			String from = request.getParameter("from_date");
			String to = request.getParameter("to_date");
			String days = request.getParameter("grds_subs");

			profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");

			if (profile == null) {
				path = "applicant_registration_step_1.jsp";
			}
			else if (StringUtils.isEmpty(from)) {
				request.setAttribute("errmsg", "Please specify from date.");
				path = "applicant_registration_step_4.jsp";
			}
			else if (StringUtils.isEmpty(to)) {
				request.setAttribute("errmsg", "Please specify to date.");
				path = "applicant_registration_step_4.jsp";
			}
			else if (StringUtils.isEmpty(days)) {
				request.setAttribute("errmsg", "Please specify approximate number of days per year.");
				path = "applicant_registration_step_4.jsp";
			}
			else {
				sdf = new SimpleDateFormat("MM/yyyy");

				ApplicantSubstituteTeachingExpBean abean = new ApplicantSubstituteTeachingExpBean();

				abean.setSIN(profile.getSIN());
				abean.setFrom(sdf.parse(from));
				abean.setTo(sdf.parse(to));
				abean.setSchoolBoard(null);
				abean.setNumDays(Integer.parseInt(days));

				ApplicantSubExpManager.addApplicantSubstituteTeachingExpBean(abean);

				request.setAttribute("msg", "Substitute teaching experience successfully added.");
				path = "applicant_registration_step_4.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant profile ESD general experience.");
			path = "applicant_registration_step_4.jsp";
		}
		catch (ParseException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Invalid date format.");
			path = "applicant_registration_step_4.jsp";
		}

		return path;
	}
}