package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.Form;

public class NotApproveApplicantRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		HttpSession session = null;

		JobOpportunityBean opp = null;
		SubListBean list = null;

		try {
			session = request.getSession();

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
			else if (request.getParameter("sin") == null) {
				request.setAttribute("msg", "SIN required to shortlist applicant.");
			}
			else {
				if (opp != null) {
					ApplicantProfileManager.shortListApplicant(request.getParameter("sin"), opp);
					session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(opp));
				}
				else if (list != null) {
					ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(request.getParameter("sin"));
					ApplicantProfileManager.applicantNotApproved(request.getParameter("sin"), list);

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
						email.setSubject("Newfoundland and Labrador English School District - Sublist Notification");
						email.setTo(profile.getEmail());
						email.setBody("Your application to subsititute list " + list.getRegion().getName() + " - "
								+ list.getTitle()
								+ " is <B><U>NOT APPROVED</U></B>.<br/><br/>Please contact the online application support person "
								+ "in your region which can be found under the teaching positions contact information section if you "
								+ "have any questions or concerns.");

						email.send();
					}
					catch (EmailException e) {}
				}

				request.setAttribute("msg", "Applicant NOT approved.");
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