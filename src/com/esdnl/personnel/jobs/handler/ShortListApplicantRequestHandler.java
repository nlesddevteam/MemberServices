package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantFilterParameters;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubListAuditBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SublistAuditTypeCostant;
import com.esdnl.personnel.jobs.dao.ApplicantFilterParametersManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantSubListAuditManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.Form;

public class ShortListApplicantRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		HttpSession session = null;
		User usr = null;

		JobOpportunityBean opp = null;
		SubListBean list = null;
		ApplicantFilterParameters abean = null;
		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")) && !(usr.getUserPermissions().containsKey("PERSONNEL-OTHER-MANAGER-VIEW"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}
			}
			else {
				throw new SecurityException("User login required.");
			}

			Form f = new Form(request);

			if (f.exists("list_id")) {
				list = SubListManager.getSubListBean(f.getInt("list_id"));
				path = "admin_view_applicant.jsp";
			}
			else if (session.getAttribute("JOB") != null) {
				opp = (JobOpportunityBean) session.getAttribute("JOB");
				path = "admin_view_job_applicants.jsp";
			}
			else if (session.getAttribute("SUBLIST") != null) {
				list = (SubListBean) session.getAttribute("SUBLIST");
				path = "admin_view_sublist_applicants.jsp";
			}

			if ((opp == null) && (list == null)) {
				request.setAttribute("msg", "Choose a job to filter applicants.");
				path = "admin_index.jsp";
			}
			else if (!f.exists("sin")) {
				request.setAttribute("msg", "SIN required to shortlist applicant.");
			}
			else {
				if (opp != null) {

					if (!opp.isShortlistComplete()) {
						ApplicantProfileManager.shortListApplicant(request.getParameter("sin"), opp);
						
						//now we add the shortlist reasons object
						if(opp.getIsSupport().equals("N")){
							if(session.getAttribute("sfilterparams") == null) {
								abean = new ApplicantFilterParameters();
								abean.setJob(opp);
								abean.setApplicantId(request.getParameter("sin"));
								abean.setShortlistedBy(usr.getPersonnel().getPersonnelID());
								abean.setShortlistReason(request.getParameter("slnotes"));
										
							}else {
								abean = (ApplicantFilterParameters) session.getAttribute("sfilterparams");
								abean.setJob(opp);
								abean.setApplicantId(request.getParameter("sin"));
								abean.setShortlistedBy(usr.getPersonnel().getPersonnelID());
							}
							//now we save the object
							ApplicantFilterParametersManager.addApplicantFilterParameters(abean);
						}else {
							//now we add the shortlist reasons object, reason not required for ss job
							abean = new ApplicantFilterParameters();
							abean.setJob(opp);
							abean.setApplicantId(request.getParameter("sin"));
							abean.setShortlistedBy(usr.getPersonnel().getPersonnelID());
							abean.setShortlistReason("");
							//now we save the object
							ApplicantFilterParametersManager.addApplicantFilterParameters(abean);
						}
						
						request.setAttribute("msg", "Applicant shortlisted successfully.");

						/*
						ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(request.getParameter("sin"));
						try {
							EmailBean email = new EmailBean();
							email.setSubject("Newfoundland and Labrador English School District - " + opp.getCompetitionNumber()
									+ " Shortlist.");
							email.setTo(profile.getEmail());
							email.setBody("You have been <B><U>SHORTLISTED</U></B> for competition " + opp.getCompetitionNumber()
									+ " - " + opp.getPositionTitle() + ".");
							email.send();
						}
						catch (EmailException e) {}
						*/
					}
					else {
						request.setAttribute("msg", "Competition shortlist has been finalized, no futher applicants can be added.");
					}

					session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(opp));
				}
				else if (list != null) {
					ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(request.getParameter("sin"));
					ApplicantProfileManager.shortListApplicant(request.getParameter("sin"), list);
					//add audit trail entry for sub list activation
					ApplicantSubListAuditBean audbean = new ApplicantSubListAuditBean();
					audbean.setApplicantId(profile.getSIN());//no applicant sub list entry
					audbean.setSubListId(list.getId());
					audbean.setEntryType(SublistAuditTypeCostant.APPLICANTAPPROVED);
					audbean.setEntryBy(usr.getPersonnel());
					audbean.setEntryNotes("Applicant Approved By: " + usr.getLotusUserFullName());
					ApplicantSubListAuditManager.addApplicantSubListAuditBean(audbean);

					if (!f.exists("list_id")) {
						session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(list));
						session.setAttribute("SHORTLIST", ApplicantProfileManager.getApplicantShortlist(list));
						session.setAttribute("NOTAPPROVEDMAP", ApplicantProfileManager.getApplicantsNotApprovedMap(list));
						session.setAttribute("NOTAPPROVED", ApplicantProfileManager.getApplicantsNotApproved(list));
					}
					else
						request.setAttribute("APPLICANT", profile);

					try {
						EmailBean email = new EmailBean();
						email.setSubject("Newfoundland and Labrador English School District - Sublist Approval");
						email.setTo(profile.getEmail());
						String sbody="You application to subsititute list " + list.getRegion().getName() + " - " + list.getTitle()
						+ " has been <B><U>APPROVED</U></B>.";
						email.setBody(sbody);
						email.send();
					}
					catch (EmailException e) {}

					request.setAttribute("msg", "Applicant shortlisted successfully.");
				}
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve applicants.");
			path = "admin_index.jsp";
		}

		return path;
	}
}