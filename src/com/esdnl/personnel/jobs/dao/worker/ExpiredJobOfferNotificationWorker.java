package com.esdnl.personnel.jobs.dao.worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TimerTask;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolFamilyDB;
import com.esdnl.personnel.jobs.bean.ApplicantPositionOfferBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.ApplicantPositionOfferManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.velocity.VelocityUtils;

public class ExpiredJobOfferNotificationWorker extends TimerTask {

	public ExpiredJobOfferNotificationWorker() {

		super();
		System.err.println("<<<<<< EXPIRED JOB OFFER NOTIFICATION TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			ApplicantPositionOfferBean[] offers = ApplicantPositionOfferManager.getExpiredPositionOfferBeans();

			System.out.println(">>>>>> EXPIRED OFFER NOTIFICATION: " + offers.length);

			TeacherRecommendationBean rec = null;
			JobOpportunityBean job = null;
			Personnel seo = null;

			ArrayList<Personnel> sendTo = null;

			for (int i = 0; i < offers.length; i++) {

				sendTo = new ArrayList<Personnel>();

				rec = offers[i].getRecommendation();
				job = offers[i].getJob();
				seo = null;

				System.out.println("EXPIRED OFFER NOTIFICATION SENT: " + job.getCompetitionNumber());

				RecommendationManager.updateTeacherRecommendationStatus(rec, null, RecommendationStatus.OFFER_EXPIRED);

				JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

				if (ass[0].getLocation() > 0) {
					School s = SchoolDB.getSchool(ass[0].getLocation());
					if (s != null) {
						sendTo.add(s.getSchoolPrincipal());

						seo = SchoolFamilyDB.getSchoolFamily(s).getProgramSpecialist();

						if (seo != null) {
							sendTo.add(seo);
						}
					}

					NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

					sendTo.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
					sendTo.addAll(Arrays.asList(mh.getRegionalHRClerks()));
				}
				else {
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")));
				}

				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

				for (Personnel p : sendTo) {
					try {
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("competitionNumber", job.getCompetitionNumber());
						model.put("competitionTitle", job.getPositionTitle());
						model.put("candidateName", rec.getCandidate().getFullNameReverse());
						model.put("userName", p.getUserName());
						model.put("recommendationId", rec.getRecommendationId());
						if (ass[0].getLocation() > 0) {
							model.put("location", ass[0].getLocationText());
						}
						else {
							model.put("location", "");
						}
						EmailBean ebean = new EmailBean();
						ebean.setSubject("Position Offer Expired - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());
						ebean.setTo(p.getEmailAddress());
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_offer_expired.vm", model));
						ebean.send();
					}
					catch (EmailException e) {
						new AlertBean(e);
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);

			try {
				new AlertBean(e);
			}
			catch (EmailException e1) {
				e1.printStackTrace();
			}
		}
	}
}