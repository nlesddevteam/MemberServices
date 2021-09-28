package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantJobAppliedManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class JobSearchRequestHandler extends RequestHandlerImpl {

	public JobSearchRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("term"), new RequiredFormElement("type")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			if (validate_form()) {

				if (form.hasValue("type", "1")) { // search by competition number
					JobOpportunityBean[] opps = JobOpportunityManager.searchJobOpportunityBeansByCompetitionNumber(form.get("term"));

					if (opps.length > 1) {
						path = "admin_job_search_results_list.jsp";

						request.setAttribute("term", form.get("term"));
						request.setAttribute("SEARCH_RESULTS", opps);
					}
					else if (opps.length == 1) {
						path = "view_job_post.jsp?comp_num=" + opps[0].getCompetitionNumber();
					}
					else {
						path = "admin_index.jsp";
						request.setAttribute("msg", "No job(s) found with competition number like \"" + form.get("term") + "\"");
					}
				}
				else if (form.hasValue("type", "2")) { // search by location
					// TODO: IMPLEMENT JOB SEARCH BY LOCATION

					path = "admin_index.jsp";
					request.setAttribute("msg", "Search by location not currently implemented.");
				}
				else if (form.hasValue("type", "3")) { // search by applicant name
					ApplicantProfileBean[] profiles = ApplicantProfileManager.searchApplicantProfileBeanByName(form.get("term"));

					if (profiles.length > 1) {
						path = "admin_applicant_search_results_list.jsp";

						request.setAttribute("term", form.get("term"));
						request.setAttribute("SEARCH_RESULTS", profiles);
					}
					else if (profiles.length == 1) {
						if(profiles[0].getProfileType().equals("S")){
							path = "admin_view_applicant_ss.jsp";
						}else{
							path = "admin_view_applicant.jsp";
						}
						

						request.setAttribute("APPLICANT", profiles[0]);
						//set the collection of applied jobs
						request.setAttribute("jobs", ApplicantJobAppliedManager.getApplicantJobsApplied(profiles[0].getSIN()));
					}
					else {
						path = "admin_index.jsp";
						request.setAttribute("msg", "No applicants found matching \"" + form.get("term") + "\"");
					}
				}else if (form.hasValue("type", "4")) { // search by applicant name
					ApplicantProfileBean[] profiles = ApplicantProfileManager.searchApplicantProfileBeanByEIDSIN(form.get("term"));

					if (profiles.length > 1) {
						path = "admin_applicant_search_results_list.jsp";

						request.setAttribute("term", form.get("term"));
						request.setAttribute("SEARCH_RESULTS", profiles);
					}
					else if (profiles.length == 1) {
						if(profiles[0].getProfileType().equals("S")){
							path = "admin_view_applicant_ss.jsp";
						}else{
							path = "admin_view_applicant.jsp";
						}
						

						request.setAttribute("APPLICANT", profiles[0]);
						//set the collection of applied jobs
						request.setAttribute("jobs", ApplicantJobAppliedManager.getApplicantJobsApplied(profiles[0].getSIN()));
					}
					else {
						path = "admin_index.jsp";
						request.setAttribute("msg", "No applicants found matching \"" + form.get("term") + "\"");
					}
				}
			}
			else {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace(System.err);

			request.setAttribute("FORM", form);
			request.setAttribute("msg", "Could not view reference.");
		}

		return path;
	}
}
