package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.security.User;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;

public class JobRecommendationControllerButtonsTagHandler extends TagSupport {

	private static final long serialVersionUID = 3892235843679630094L;

	private TeacherRecommendationBean recommendation = null;

	public void setRecommendation(TeacherRecommendationBean recommendation) {

		this.recommendation = recommendation;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		User usr = null;
		try {
			out = pageContext.getOut();
			usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

			if (usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION") && recommendation.isApproved()
					&& !recommendation.isAccepted() && !recommendation.isRejected()) {
					out.println("<label class='checkbox-inline'><input type='checkbox' onclick='COC_Check(this)'/>CODE OF CONDUCT is satisfactory?</label>");
					out.println("<BR>");
			}
			
			if (usr.checkPermission("PERSONNEL-ADMIN-VIEW")
					&& (pageContext.getAttribute("JOB_APPLICANTS", PageContext.SESSION_SCOPE) != null)) {
					out.println("<a href='admin_view_job_applicants.jsp' class='btn btn-xs btn-danger'>Back</a>");
			}

			if (recommendation.isRecommended() && !recommendation.isApproved() && !recommendation.isRejected()) {
				if (recommendation.getJob().getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
						|| recommendation.getJob().getJobType().equals(JobTypeConstant.LEADERSHIP)) {
					if (usr.checkRole("DIRECTOR") || usr.checkRole("ASSOCIATE ASSISTANT DIRECTOR")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-success' op='approve' href='#'>Approve Recommendation</a>");
						out.println("<a class='rec-op-btn btn btn-xs btn-danger' op='reject' href='#'>Reject Recommendation</a>");
					}
				}
				else {
					if (usr.checkPermission("PERSONNEL-ADMIN-APPROVE-RECOMMENDATION") || usr.checkRole("DIRECTOR")
							|| usr.checkRole("ASSOCIATE ASSISTANT DIRECTOR")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-success' op='approve' href='#'>Approve Recommendation</a>");
						out.println("<a class='rec-op-btn btn btn-xs btn-danger' op='reject' href='#'>Reject Recommendation</a>");
					}
				}

				out.println("<button type='button' class='resend-notifications btn btn-xs btn-warning' href='javascript:void(0);'>Resend Notifications</button>");
			}

			if (usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION") && recommendation.isApproved()
					&& !recommendation.isAccepted() && !recommendation.isRejected()) {
				out.println("<a class='rec-op-btn btn btn-xs btn-success' op='accept' style='display:none;' id='accept_btn' href='#'>Accept Recommendation</a>");
				out.println("<a class='rec-op-btn btn btn-xs btn-danger' op='reject' href='#'>Reject Recommendation</a>");
			}

			if (recommendation.isApproved() && recommendation.isAccepted() && !recommendation.isOfferMade()) {
				if (recommendation.getJob().getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
						|| recommendation.getJob().getJobType().equals(JobTypeConstant.LEADERSHIP)) {
					if (usr.checkRole("AD HR") || usr.checkRole("ASSOCIATE ASSISTANT DIRECTOR")
							|| usr.checkRole("PERSONNEL-CENTRAL-AD-PROGRAMS") || usr.checkRole("PERSONNEL-WESTERN-AD-PROGRAMS")
							|| usr.checkRole("PERSONNEL-LABRADOR-AD-PROGRAMS") || usr.checkRole("PERSONNEL-EASTERN-AD-PROGRAMS")
							|| usr.checkRole("SEO - PERSONNEL")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-primary' op='offer'  id='offer_btn' href='#'>Make Offer</a>");

					}
				}
				else {
					if (usr.checkPermission("PERSONNEL-ADMIN-OFFER-POSITION")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-primary' op='offer'  id='offer_btn' href='#'>Make Offer</a>");
					}
				}
			}

			if (recommendation.isApproved() && recommendation.isAccepted() && recommendation.isOfferMade()
					&& recommendation.isExpired()) {
				if (recommendation.getJob().getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
						|| recommendation.getJob().getJobType().equals(JobTypeConstant.LEADERSHIP)) {
					if (usr.checkRole("AD HR") || usr.checkRole("ASSOCIATE ASSISTANT DIRECTOR")
							|| usr.checkRole("PERSONNEL-CENTRAL-AD-PROGRAMS") || usr.checkRole("PERSONNEL-WESTERN-AD-PROGRAMS")
							|| usr.checkRole("PERSONNEL-LABRADOR-AD-PROGRAMS") || usr.checkRole("PERSONNEL-EASTERN-AD-PROGRAMS")
							|| usr.checkRole("SEO - PERSONNEL")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-warning' op='resend_offer' href='#'>Resend Offer</a>");
					}
				}
				else {
					if (usr.checkPermission("PERSONNEL-ADMIN-RESEND-POSITION-OFFER")) {
						out.println("<a class='rec-op-btn btn btn-xs btn-warning' op='resend_offer' href='#'>Resend Offer</a>");
					}
				}
			}

			if (usr.checkPermission("PERSONNEL-ADMIN-PROCESS-RECOMMENDATION") && recommendation.isApproved()
					&& recommendation.isAccepted() && recommendation.isOfferAccepted() && !recommendation.isProcessed()) {
				out.println("<a class='rec-op-btn btn btn-xs btn-primary' op='process' href='#'>Process Recommendation</a>");
			}

			if (usr.checkPermission("PERSONNEL-ADMIN-PROCESS-RECOMMENDATION") && recommendation.isProcessed()) {
				out.println("<a class='btn btn-xs btn-info' href='jobRecommentationController.html?op=print_rec&id="+ recommendation.getRecommendationId()+"'>Print Offer</a>");

			}

			out.println("</TR>");
			out.println("</TABLE>");

		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

}
