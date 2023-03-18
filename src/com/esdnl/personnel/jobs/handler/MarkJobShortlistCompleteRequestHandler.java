package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolFamilyDB;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;

public class MarkJobShortlistCompleteRequestHandler extends RequestHandlerImpl {

	public MarkJobShortlistCompleteRequestHandler() {

		requiredRoles = new String[] { "ADMINISTRATOR", "SEO - PERSONNEL", "SENIOR EDUCATION OFFICIER", "AD HR",
				"ASSISTANT DIRECTORS" };

		validator = new FormValidator(
				new FormElement[] { new RequiredFormElement("comp_num"), new RequiredFormElement("closed") });
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				JobOpportunityBean opp = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if (opp != null) {
					if (form.getBoolean("closed")) {
						opp.setShortlistCompleteDate(new Date());
						//send email to school admin if department head or replacement position
						if(opp.getIsSupport().contentEquals("N")) {
							if(opp.getJobType() == JobTypeConstant.DEPARTMENT_HEAD || opp.getJobType() == JobTypeConstant.REPLACEMENT) {
								JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);
								if (ass[0].getLocation() > 0) {
									Personnel seo = null;
									School s = SchoolDB.getSchool(ass[0].getLocation());
									if(s.getSchoolDeptID() > 0) {
										seo = SchoolFamilyDB.getSchoolFamily(s).getProgramSpecialist();
									}
									try {
										ArrayList<Personnel> to = new ArrayList<Personnel>();
										if (seo != null) {
											to.add(seo);
										}

										if (s != null && s.getSchoolPrincipal() != null) {
											to.add(s.getSchoolPrincipal());
										}
										
										if (to.size() > 0) {
											for (Personnel p : to) {
												EmailBean ebean = new EmailBean();
												HashMap<String, Object> model = new HashMap<String, Object>();
												ebean.setSubject(
														"Shortlist marked as completed - " + opp.getCompetitionNumber() + ": " + opp.getPositionTitle());
												// set values to be used in template
												model.put("competitionNumber", opp.getCompetitionNumber());
												model.put("competitionTitle", opp.getPositionTitle());
												if (ass[0].getLocation() > 0) {
													model.put("location", ass[0].getLocationText());
												}
												else {
													model.put("location", "");
												}
												// send email to principal
												ebean.setTo(p.getEmailAddress());
												ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/shortlist_marked_completed.vm", model));
												ebean.setFrom("ms@nlesd.ca");
												ebean.send();
											}
										}
									}
									catch (EmailException e) {
										e.printStackTrace();
									}
								
								}
							}
						}
					}
					else {
						opp.setShortlistCompleteDate(null);
					}

					JobOpportunityManager.updateJobOpportunityBean(opp);

					session.setAttribute("JOB", opp);
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(opp));
					session.setAttribute("JOB_SHORTLIST_DECLINES_MAP",
							ApplicantProfileManager.getApplicantShortlistInterviewDeclinesMap(opp));
					if(opp.getIsSupport().contentEquals("Y")) {
						RequestToHireBean rth = RequestToHireManager.getRequestToHireByCompNum(form.get("comp_num"));
						request.setAttribute("AD_REQUEST", rth);
					}else {
						AdRequestBean ad = AdRequestManager.getAdRequestBean(form.get("comp_num"));
						request.setAttribute("AD_REQUEST", ad);
					}
					
					path = "admin_view_job_applicants_shortlist.jsp";
				}
				else {
					request.setAttribute("msg", "Invalid reques.");

					path = "admin_index.jsp";
				}
		}
			else {
				request.setAttribute("msg", "Cometition number required to view applicant shortlist.");

				path = "admin_view_job_posts.jsp";
			}
		}catch(

	JobOpportunityException e)
	{
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_job_posts.jsp";
		}

	return path;
}}