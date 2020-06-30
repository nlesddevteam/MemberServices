package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolFamily;
import com.awsd.school.SchoolFamilyDB;
import com.awsd.school.SchoolFamilyException;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewJobApplicantsRequestHandler extends RequestHandlerImpl {

	public ViewJobApplicantsRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		ApplicantProfileBean[] profiles = null;
		JobOpportunityBean opp = null;

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				SchoolFamily family = null;
				try {
					family = ((opp.get(0) != null) && (opp.get(0).getSchool() != null))
							? SchoolFamilyDB.getSchoolFamily(opp.get(0).getSchool())
							: null;
				}
				catch (SchoolFamilyException e) {
					family = null;
				}

				boolean isFOS = ((family != null) && (usr.getPersonnel().getPersonnelID() == family.getProgramSpecialistID()));

				if (!opp.isCandidateListPrivate() || usr.checkPermission("PERSONNEL-ADMIN-VIEW-PRIVATE-CANDIDATE-LIST")
						|| isFOS) {

					if (opp.getJobType().equal(JobTypeConstant.LEADERSHIP) && usr.checkRole("SENIOR EDUCATION OFFICIER")
							&& !usr.checkRole("JOB APPS - VIEW PRIVATE")) {
						request.setAttribute("msg", "Applicant List for competition  " + form.get("comp_num") + " is private.");
						path = "admin_index.jsp";
					}
					else {
						profiles = ApplicantProfileManager.getApplicantProfileBeanByJob(form.get("comp_num"));

						session.setAttribute("JOB_APPLICANTS", profiles);
						session.setAttribute("JOB", opp);
						session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(opp));

						path = "admin_view_job_applicants.jsp";
					}
				}
				else {
					request.setAttribute("msg", "Applicant List for competition  " + form.get("comp_num") + " is private.");
					path = "admin_index.jsp";
				}
			}
			else {
				request.setAttribute("msg", "Cometition number required to view applicants.");
				path = "admin_index.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_job_posts.jsp";
		}

		return path;
	}
}