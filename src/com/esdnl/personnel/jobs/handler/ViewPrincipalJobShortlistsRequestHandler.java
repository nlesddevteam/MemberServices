package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;

public class ViewPrincipalJobShortlistsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				if (!(usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")
						|| usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}
			}
			else {
				throw new SecurityException("User login required.");
			}

			//filter only job with completed shortlists
			List<JobOpportunityBean> jobs = Arrays.asList(
					JobOpportunityManager.getJobOpportunityBeans(usr.getPersonnel().getSchool().getSchoolID())).stream().filter(
							j -> j.isShortlistComplete() && !j.isCandidateListPrivate() && !j.isSupport()).collect(
									Collectors.toList());

			request.setAttribute("PRINCIPAL_SHORTLISTS", (JobOpportunityBean[]) jobs.toArray(new JobOpportunityBean[0]));
			path = "admin_view_principal_job_shortlists.jsp";

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "";
		}

		return path;
	}
}