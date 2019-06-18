package com.esdnl.personnel.jobs.handler;

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
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class RecommendationControllerRequestHandler extends RequestHandlerImpl {

	public RecommendationControllerRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-ADMIN-APPROVE-RECOMMENDATION", "PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION",
				"PERSONNEL-ADMIN-PROCESS-RECOMMENDATION", "PERSONNEL-ADMIN-OFFER-POSITION"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id"), new RequiredFormElement("op")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_view_job_teacher_recommendation.jsp";

		if (validate_form()) {
			TeacherRecommendationBean rec = null;
			try {
				rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				if (StringUtils.isEqual(form.get("op"), "approve")
						&& usr.checkPermission("PERSONNEL-ADMIN-APPROVE-RECOMMENDATION")) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, usr.getPersonnel(),
							RecommendationStatus.APPROVED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					this.sendNotification(rec, form.get("op"));

					request.setAttribute("msg", "Recommendation approval has been received.");
				}
				else if (StringUtils.isEqual(form.get("op"), "reject")
						&& (usr.checkPermission("PERSONNEL-ADMIN-APPROVE-RECOMMENDATION") || usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION"))) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, usr.getPersonnel(),
							RecommendationStatus.REJECTED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					this.sendNotification(rec, form.get("op"));

					request.setAttribute("msg", "Recommendation rejection has been received.");
				}
				else if (StringUtils.isEqual(form.get("op"), "accept")
						&& usr.checkPermission("PERSONNEL-ADMIN-ACCEPT-RECOMMENDATION")) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, usr.getPersonnel(),
							RecommendationStatus.ACCEPTED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					AdRequestBean req = AdRequestManager.getAdRequestBean(rec.getJob().getCompetitionNumber());
					if (req != null) {
						boolean update = false;

						if (form.exists("start_date")) {
							req.setStartDate(form.getDate("start_date"));
							update = true;
						}

						if (form.exists("end_date")) {
							req.setEndDate(form.getDate("end_date"));
							update = true;
						}

						if (update)
							AdRequestManager.updateAdRequestBean(req);
					}

					this.sendNotification(rec, form.get("op"));

					request.setAttribute("msg", "Recommendation acceptance has been received.");
				}
				else if (StringUtils.isEqual(form.get("op"), "offer") && usr.checkPermission("PERSONNEL-ADMIN-OFFER-POSITION")) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, usr.getPersonnel(), RecommendationStatus.OFFERED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					this.sendNotification(rec, form.get("op"));

					request.setAttribute("msg", "Offer has been sent to " + rec.getCandidate().getFullNameReverse() + ".");
				}
				else if (StringUtils.isEqual(form.get("op"), "resend_offer")
						&& usr.checkPermission("PERSONNEL-ADMIN-RESEND-POSITION-OFFER")) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, rec.getOfferMadeByPersonnel(),
							RecommendationStatus.OFFERED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					this.sendNotification(rec, form.get("op"));

					request.setAttribute("msg", "Offer has been re-sent to " + rec.getCandidate().getFullNameReverse() + ".");
				}
				else if (StringUtils.isEqual(form.get("op"), "process")
						&& usr.checkPermission("PERSONNEL-ADMIN-PROCESS-RECOMMENDATION")) {
					RecommendationManager.updateTeacherRecommendationStatus(rec, usr.getPersonnel(),
							RecommendationStatus.PROCESSED);
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

					JobOpportunityManager.awardJobOpportunityBean(rec.getJob().getCompetitionNumber());

					request.setAttribute("msg", "Recommendation has been processed successfully.");
				}
				else if (StringUtils.isEqual(form.get("op"), "print_rec")
						&& usr.checkPermission("PERSONNEL-ADMIN-PROCESS-RECOMMENDATION")) {
					rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));
					if(rec.getJob().getIsSupport().equals("N")){
						path = "printable_job_teacher_recommendation.jsp";
					}else{
						RequestToHireBean rbean = RequestToHireManager.getRequestToHireByCompNum(rec.getCompetitionNumber());
						request.setAttribute("rbean", rbean);
						path = "printable_job_teacher_recommendation_ss.jsp";
					}

					
				}

				request.setAttribute("RECOMMENDATION_BEAN", rec);
			}
			catch (JobOpportunityException e) {
				try {
					new AlertBean(e);
				}
				catch (EmailException ee) {}

				e.printStackTrace(System.err);

				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Could not view recommendation.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
		}

		return path;
	}

	private void sendNotification(TeacherRecommendationBean rec, String op) {

		try {

			if (op.equalsIgnoreCase("approve"))
				sendApprovedNotification(rec);

			else if (op.equalsIgnoreCase("reject"))
				sendRejectedNotification(rec);

			else if (op.equalsIgnoreCase("accept"))
				sendAcceptanceNotification(rec);

			else if (op.equalsIgnoreCase("offer") || op.equalsIgnoreCase("resend_offer"))
				sendOfferNotification(rec);
		}
		catch (Exception e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}
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

			ebean.setSubject("Position Recommendation REJECTED - " + job.getCompetitionNumber() + ": "
					+ job.getPositionTitle());

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

		//ArrayList<SEOStaffingAssignmentBean> staffing = SEOStaffingAssignmentManager.getSEOStaffingAssignmentBeans(job);
		School s = null;

		if (ass[0].getLocation() > 0) {
			s = SchoolDB.getSchool(ass[0].getLocation());
		}

		/*
		 * Chris - June 12, 2015 - remove staffing assignments.
		 */

		/*
		if (staffing.size() > 0) {
			for (SEOStaffingAssignmentBean a : staffing)
				sendTo.add(a.getPersonnel());
		}
		*/

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

		if (ass[0].getLocation() > 0
				&& !(job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE) || job.getJobType().equals(
						JobTypeConstant.LEADERSHIP))) {

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

				ebean.setSubject("Position Offer Ready To Be Made - " + job.getCompetitionNumber() + ": "
						+ job.getPositionTitle());
				ebean.setTo(p.getEmailAddress());

				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("recommendName", usr.getPersonnel().getFullNameReverse());
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
			model.put("jobDetails", job.toHTML());
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