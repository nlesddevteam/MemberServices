package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantRegionalPreferenceManager;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;

public class AddApplicantRegionalPreferencesRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		int[] regions = form.getIntArray("region_id");

		try {
			if (regions != null && regions.length > 0) {
				ApplicantRegionalPreferenceManager.addApplicantRegionalPreferences(profile, regions);

				request.setAttribute("msg", "Regional preferences updated successfully.");
			}
			else {
				ApplicantRegionalPreferenceManager.clearApplicantRegionalPreferences(profile);
				request.setAttribute("errmsg", "Please select your regional preferences.");
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace(System.err);
			request.setAttribute("errmsg", e.getMessage());
		}

		path = "applicant_registration_step_9.jsp";

		return path;

	}

}
