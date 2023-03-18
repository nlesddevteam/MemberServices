package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.SchoolFamilyDB;
import com.awsd.school.SchoolFamilyException;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class ResendTeacherRecommendationNotificationsAjaxRequestHandler extends RequestHandlerImpl {

	public ResendTeacherRecommendationNotificationsAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id", "Recommendation ID is required."),
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String msg = "";

				TeacherRecommendationBean rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				if (rec != null) {
					sendNotification(rec);

					msg = "Notification sent successfully.";
				}
				else {
					msg = "Recommendation [ID: " + form.getInt("id") + "] could not be found.";
				}

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<RESEND-TEACHER-RECOMMENDATION-NOTIFICATIONS-RESPONSE msg='" + msg + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<RESEND-TEACHER-RECOMMENDATION-NOTIFICATIONS-RESPONSE msg='Notifications could not be resent.<br />"
						+ StringUtils.encodeHTML2(e.getMessage()) + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

			sb.append(
					"<RESEND-TEACHER-RECOMMENDATION-NOTIFICATIONS-RESPONSE msg='" + this.validator.getErrorString() + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

	private void sendNotification(TeacherRecommendationBean rec) {

		try {

			if (rec.isRecommended() && !rec.isApproved() && !rec.isRejected()) {
				sendRecommendationNotification(rec);
			}
		}
		catch (Exception e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}
		}
	}

	private void sendRecommendationNotification(TeacherRecommendationBean rec) {

		try {
			ArrayList<Personnel> sendTo = new ArrayList<Personnel>();

			JobOpportunityBean job = rec.getJob();
			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

			//check to see what type of position it is ADMIN/LEADERSHIP should goto director
			if (job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
					|| job.getJobType().equals(JobTypeConstant.LEADERSHIP)) {
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ASSOCIATE ASSISTANT DIRECTOR")));
			}
			else {
				if (sendTo.size() <= 0) {
					if (ass[0].getLocation() > 0) {//send to SEO for Region
						NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

						if (mh != null) {
							sendTo.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
						}
					}
					else {
						sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")));
					}
				}

				// send email HR ADE
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));

				sendTo.remove(rec.getRecommendedByPersonnel());
			}

			sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

			for (Personnel p : sendTo) {
				try {

					EmailBean ebean = new EmailBean();
					HashMap<String, Object> model = new HashMap<String, Object>();
					ebean.setSubject("Position Recommendation Approval Required - " + job.getCompetitionNumber() + ": "
							+ job.getPositionTitle());
					// set values to be used in template
					model.put("recommendName", rec.getRecommendedByPersonnel().getFullNameReverse());
					model.put("candidateName", rec.getCandidate().getFullNameReverse());
					model.put("competitionNumber", job.getCompetitionNumber());
					model.put("competitionTitle", job.getPositionTitle());
					model.put("userName", p.getUserName());
					model.put("recommendationId", rec.getRecommendationId());
					if (ass[0].getLocation() > 0) {
						model.put("location", ass[0].getLocationText());
					}
					else {
						model.put("location", "");
					}
					ebean.setTo(p.getEmailAddress());
					ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_recommendation_approval.vm", model));
					ebean.setFrom("ms@nlesd.ca");
					ebean.send();

				}
				catch (Exception e) {
					try {
						new AlertBean(e);
					}
					catch (EmailException ee) {}

					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}

			e.printStackTrace();
		}
	}

	private void sendApprovedNotification(TeacherRecommendationBean rec)
			throws PersonnelException,
				JobOpportunityException {

		ArrayList<Personnel> sendTo = new ArrayList<Personnel>();

		JobOpportunityBean job = rec.getJob();

		try {

			if (job.get(0).getLocationZone() != null) {
				NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(job.get(0).getLocationZone().getZoneId());

				sendTo.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
				//check to see if admin or leadership position
				if (job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
						|| job.getJobType().equals(JobTypeConstant.LEADERSHIP)) {
					sendTo.addAll(Arrays.asList(mh.getRegionalADHRAssistant()));
				}
				else {
					sendTo.addAll(Arrays.asList(mh.getRegionalHRClerks()));
				}

			}
			else {
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION")));
			}

			sendTo.remove(rec.getApprovedByPersonnel());

			sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

			sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));

			EmailBean ebean = new EmailBean();

			ebean.setSubject("Position Recommendation Acceptance Required - " + job.getCompetitionNumber() + ": "
					+ job.getPositionTitle());
			for (Personnel p : sendTo) {
				ebean.setTo(p.getEmailAddress());
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("recommendName", usr.getPersonnel().getFullNameReverse());
				model.put("candidateName", rec.getCandidate().getFullNameReverse());
				model.put("userName", p.getUserName());
				model.put("recommendationId", rec.getRecommendationId());
				model.put("competitionNumber", job.getCompetitionNumber());
				model.put("competitionTitle", job.getPositionTitle());
				if (job.getAssignments()[0].getLocation() > 0) {
					model.put("location", job.getAssignments()[0].getLocation());
				}
				else {
					model.put("location", "");
				}
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_recommendation_approved.vm", model));
				ebean.setFrom("ms@nlesd.ca");
				ebean.send();

			}
		}
		catch (EmailException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}

			e.printStackTrace();
		}
	}

	private void sendRejectedNotification(TeacherRecommendationBean rec)
			throws JobOpportunityException,
				PersonnelException {

		JobOpportunityBean job = rec.getJob();

		try {
			EmailBean ebean = new EmailBean();

			ebean.setSubject(
					"Position Recommendation REJECTED - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());

			if (rec.isApproved())
				ebean.setTo(rec.getApprovedByPersonnel().getEmailAddress());
			else
				ebean.setTo(rec.getRecommendedByPersonnel().getEmailAddress());

			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("recommendName", usr.getPersonnel().getFullNameReverse());
			model.put("candidateName", rec.getCandidate().getFullNameReverse());
			model.put("competitionNumber", job.getCompetitionNumber());
			model.put("competitionTitle", job.getPositionTitle());
			if (job.getAssignments()[0].getLocation() > 0) {
				model.put("location", job.getAssignments()[0].getLocation());
			}
			else {
				model.put("location", "");
			}
			ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_recommendation_rejected.vm", model));
			ebean.setFrom("ms@nlesd.ca");
			ebean.send();
		}
		catch (EmailException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}

			e.printStackTrace();
		}
	}

	private void sendAcceptanceNotification(TeacherRecommendationBean rec)
			throws JobOpportunityException,
				PersonnelException,
				SchoolException,
				SchoolFamilyException {

		ArrayList<Personnel> sendTo = new ArrayList<Personnel>();

		JobOpportunityBean job = rec.getJob();
		JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

		School s = null;

		if (ass[0].getLocation() > 0) {
			s = SchoolDB.getSchool(ass[0].getLocation());
		}

		if (ass[0].getLocation() > 0) {
			Personnel ps = null;

			if (s != null) {
				try {
					ps = SchoolFamilyDB.getSchoolFamily(s).getProgramSpecialist();
				}
				catch (SchoolFamilyException e) {
					try {
						EmailBean alert = new EmailBean();
						alert.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR "));
						alert.setSubject("SchoolFamilyException: No family set for location id[" + ass[0].getLocation() + "]");

						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						alert.setBody(sw.toString());

						alert.send();
					}
					catch (EmailException ex) {}
				}
			}

			if (ps != null) {
				sendTo.add(ps);
			}
		}

		if (ass[0].getLocation() > 0 && !(job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
				|| job.getJobType().equals(JobTypeConstant.LEADERSHIP))) {

			if (s != null && s.getSchoolPrincipal() != null) {
				sendTo.add(s.getSchoolPrincipal());
			}
		}

		if (ass[0].getLocationZone() != null) {
			NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

			sendTo.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
			//send to regional AD
			if (job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
					|| job.getJobType().equals(JobTypeConstant.LEADERSHIP)) {
				sendTo.addAll(Arrays.asList(mh.getRegionalADPrograms()));
			}
		}
		else {
			sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")));
			//can't determine region send to all ad
			sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));
		}

		sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

		for (Personnel p : sendTo) {
			try {
				EmailBean ebean = new EmailBean();

				ebean.setSubject(
						"Position Offer Ready To Be Made - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());
				ebean.setTo(p.getEmailAddress());

				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("recommendName", usr.getPersonnel().getFullNameReverse());
				model.put("candidateName", rec.getCandidate().getFullNameReverse());
				model.put("competitionNumber", job.getCompetitionNumber());
				model.put("competitionTitle", job.getPositionTitle());
				if (ass[0].getLocation() > 0) {
					model.put("location", ass[0].getLocationText());
				}
				else {
					model.put("location", "");
				}
				model.put("userName", p.getUserName());
				model.put("recommendationId", rec.getRecommendationId());
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_recommendation_accepted.vm", model));
				ebean.setFrom("ms@nlesd.ca");
				ebean.send();
			}
			catch (EmailException e) {
				try {
					new AlertBean(e);
				}
				catch (EmailException ee) {}

				e.printStackTrace();
			}
		}
	}

	private void sendOfferNotification(TeacherRecommendationBean rec) throws JobOpportunityException {

		JobOpportunityBean job = rec.getJob();
		ApplicantProfileBean profile = rec.getCandidate();

		try {
			EmailBean ebean = new EmailBean();
			ebean.setSubject("Newfoundland and Labrador English School District - Position Offer - "
					+ job.getCompetitionNumber() + ": " + job.getPositionTitle());
			ebean.setTo(profile.getEmail());

			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("jobDetails", job.toHTMLNoAd(rec));
			model.put("expiryDate", rec.getOfferValidDateFormatted());
			ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_offer_app.vm", model));
			ebean.setFrom("ms@nlesd.ca");
			ebean.send();
		}
		catch (EmailException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}

			e.printStackTrace();
		}
	}

}
