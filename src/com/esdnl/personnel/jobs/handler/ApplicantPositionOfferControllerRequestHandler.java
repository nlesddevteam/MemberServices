package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolFamilyDB;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class ApplicantPositionOfferControllerRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public ApplicantPositionOfferControllerRequestHandler() {

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				ApplicantProfileBean profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");

				TeacherRecommendationBean rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				// check to ensure that we have a valid offer and the correct applicant
				// is looking at this position offer, if not redirect to login page

				if ((rec == null) || !profile.getSIN().equalsIgnoreCase(rec.getCandidateId()))
					response.sendRedirect("/MemberServices/Personnel/applicant_login.jsp");

				if (!StringUtils.isEmpty(form.get("op"))) {
					if (StringUtils.isEqual(form.get("op"), "accept")) {

						validator = new FormValidator(new FormElement[] {
								new RequiredFormElement("id"), new RequiredFormElement("sin2"), new RequiredFormElement("dob"),
						});
						if (validate_form()) {
							RecommendationManager.updateTeacherRecommendationStatus(rec, null, RecommendationStatus.OFFER_ACCEPTED);

							profile.setSIN2(form.get("sin2"));
							profile.setDOB(form.getDate("dob"));

							ApplicantProfileManager.updateApplicantProfileBean(profile);

							ApplicantProfileManager.applicantWorking(profile);
							JobOpportunityBean job = rec.getJob();
							JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(
									job);

							// send confirmation email to applicant indicating there
							// acceptance has been received
							try {
								EmailBean ebean = new EmailBean();
								HashMap<String, Object> model = new HashMap<String, Object>();
								ebean.setTo(profile.getEmail());
								ebean.setSubject(
										"Newfoundland and Labrador English School District - Position Offer Acceptance Confirmation - "
												+ job.getCompetitionNumber() + ": " + job.getPositionTitle());
								// set values to be used in template
								model.put("competitionNumber", job.getCompetitionNumber());
								model.put("competitionTitle", job.getPositionTitle());
								if (ass[0].getLocation() > 0) {
									model.put("location", ass[0].getLocationText());
								}
								else {
									model.put("location", "");
								}
								ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_offer_accepted_app.vm", model));
								ebean.setFrom("ms@nlesd.ca");
								ebean.send();
								//now we get all other applicants and send a position filled email
								ApplicantProfileBean[] profiles = null;
								profiles = ApplicantProfileManager.getApplicantProfileBeanByJob(job.getCompetitionNumber());
								for (ApplicantProfileBean apb : profiles) {
									if (!(apb.getEmail().equals(profile.getEmail()))) {
										EmailBean ebeanother = new EmailBean();
										ebeanother.setTo(apb.getEmail());
										ebeanother.setSubject("Newfoundland and Labrador English School District - Position Filled - "
												+ job.getCompetitionNumber() + ": " + job.getPositionTitle());
										// set values to be used in template
										model.put("competitionNumber", job.getCompetitionNumber());
										model.put("competitionTitle", job.getPositionTitle());
										model.put("candidateName", org.apache.commons.lang.StringUtils.capitalize(profile.getFirstname())
												+ " " + org.apache.commons.lang.StringUtils.capitalize(profile.getSurname()));
										ebeanother.setBody(
												VelocityUtils.mergeTemplateIntoString("personnel/position_filled_response.vm", model));
										ebeanother.setFrom("ms@nlesd.ca");
										ebeanother.send();
									}
								}

							}
							catch (EmailException e) {
								e.printStackTrace();
							}

							if (ass[0].getLocation() > 0) {
								School s = SchoolDB.getSchool(ass[0].getLocation());
								Personnel seo = SchoolFamilyDB.getSchoolFamily(s).getProgramSpecialist();
								try {
									ArrayList<Personnel> to = new ArrayList<Personnel>();

									if (seo != null) {
										to.add(seo);
									}

									if (s != null && s.getSchoolPrincipal() != null
											&& !(job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
													|| job.getJobType().equals(JobTypeConstant.LEADERSHIP))) {
										to.add(s.getSchoolPrincipal());
									}

									if (to.size() > 0) {
										for (Personnel p : to) {

											EmailBean ebean = new EmailBean();
											HashMap<String, Object> model = new HashMap<String, Object>();
											ebean.setSubject(
													"Position Offer Accepted - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());
											// set values to be used in template
											model.put("candidateName", profile.getFullNameReverse());
											model.put("competitionNumber", job.getCompetitionNumber());
											model.put("competitionTitle", job.getPositionTitle());
											if (ass[0].getLocation() > 0) {
												model.put("location", ass[0].getLocationText());
											}
											else {
												model.put("location", "");
											}
											// send email to principal
											ebean.setTo(p.getEmailAddress());
											ebean.setBody(
													VelocityUtils.mergeTemplateIntoString("personnel/position_offer_accepted.vm", model));
											ebean.setFrom("ms@nlesd.ca");
											ebean.send();
										}
									}
								}
								catch (EmailException e) {
									e.printStackTrace();
								}
							}

							// send to regional SEO and AD HR
							ArrayList<Personnel> to = new ArrayList<Personnel>();

							if (ass[0].getLocationZone() != null) {
								NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

								to.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
								to.addAll(Arrays.asList(mh.getRegionalHRClerks()));
							}
							else {
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")));
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-CLERK")));
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-CLERK")));
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-CLERK")));
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-CLERK")));
							}

							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));
							//send email to HR Pension/benefits for support staff positions
							if(job.getIsSupport().equals("Y")) {
								to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-SUPPORT-BENEFITS")));
							}
							//send to comptroller group for all jobs support/teaching RTH-BC
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-BC")));
							
							try {
								EmailBean ebean = new EmailBean();

								for (Personnel p : to) {

									HashMap<String, Object> model = new HashMap<String, Object>();
									ebean.setSubject(profile.getFullNameReverse() + " has ACCEPTED the position - "
											+ job.getCompetitionNumber() + ": " + job.getPositionTitle());
									// set values to be used in template
									model.put("requesterName", p.getFullNameReverse());
									model.put("candidateName", profile.getFullNameReverse());
									model.put("userName", p.getUserName());

									model.put("recommendationId", rec.getRecommendationId());
									model.put("competitionNumber", job.getCompetitionNumber());
									model.put("competitionTitle", job.getPositionTitle());
									if (ass[0].getLocation() > 0) {
										model.put("location", ass[0].getLocationText());
									}
									else {
										model.put("location", "");
									}
									ebean.setTo(p.getEmailAddress());
									ebean.setBody(
											VelocityUtils.mergeTemplateIntoString("personnel/position_offer_accepted_admin.vm", model));
									ebean.setFrom("ms@nlesd.ca");
									ebean.send();

								}
							}
							catch (EmailException e) {
								e.printStackTrace();
							}
							rec = RecommendationManager.getTeacherRecommendationBean(rec.getRecommendationId());
							request.setAttribute("msg", "Your acceptance of the offer has been received, Congratulations!");
						}
						else
							request.setAttribute("msg",
									"Social Insurance Number (xxx-xxx-xxx) and Date of Birth (dd/mm/yyyy) are required to accept your offer.");
					}

					else if (StringUtils.isEqual(form.get("op"), "reject")) {
						RecommendationManager.updateTeacherRecommendationStatus(rec, null, RecommendationStatus.OFFER_REJECTED);

						JobOpportunityBean job = rec.getJob();
						JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

						// send confirmation email to applicant indicating there acceptance
						// has been received
						try {
							EmailBean ebean = new EmailBean();
							HashMap<String, Object> model = new HashMap<String, Object>();
							ebean.setTo(profile.getEmail());
							ebean.setSubject(
									"Newfoundland and Labrador English School District - Position Offer Decline Confirmation - "
											+ job.getCompetitionNumber() + ": " + job.getPositionTitle());
							// set values to be used in template
							model.put("competitionNumber", job.getCompetitionNumber());
							model.put("competitionTitle", job.getPositionTitle());
							if (ass[0].getLocation() > 0) {
								model.put("location", ass[0].getLocationText());
							}
							else {
								model.put("location", "");
							}
							ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_offer_rejected_app.vm", model));
							ebean.setFrom("ms@nlesd.ca");
							ebean.send();
						}
						catch (EmailException e) {
							e.printStackTrace();
						}

						//send to SEO and Principal
						if (ass[0].getLocation() > 0) {
							School s = SchoolDB.getSchool(ass[0].getLocation());
							Personnel seo = SchoolFamilyDB.getSchoolFamily(s).getProgramSpecialist();
							try {
								ArrayList<Personnel> to = new ArrayList<Personnel>();

								if (seo != null) {
									to.add(seo);
								}

								if (s != null && s.getSchoolPrincipal() != null
										&& !(job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
												|| job.getJobType().equals(JobTypeConstant.LEADERSHIP))) {
									to.add(s.getSchoolPrincipal());
								}

								if (to.size() > 0) {
									for (Personnel p : to) {
										EmailBean ebean = new EmailBean();
										HashMap<String, Object> model = new HashMap<String, Object>();
										ebean.setSubject(
												"Position Offer REJECTED - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());
										// set values to be used in template
										model.put("candidateName", profile.getFullNameReverse());
										model.put("competitionNumber", job.getCompetitionNumber());
										model.put("competitionTitle", job.getPositionTitle());
										if (ass[0].getLocation() > 0) {
											model.put("location", ass[0].getLocationText());
										}
										else {
											model.put("location", "");
										}
										// send email to principal
										ebean.setTo(p.getEmailAddress());
										ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/position_offer_rejected.vm", model));
										ebean.setFrom("ms@nlesd.ca");
										ebean.send();
									}
								}

							}
							catch (EmailException e) {
								e.printStackTrace();
							}
						}

						// send to regional SEO and AD HR
						ArrayList<Personnel> to = new ArrayList<Personnel>();

						if (ass[0].getLocationZone() != null) {
							NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

							to.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
							to.addAll(Arrays.asList(mh.getRegionalHRClerks()));
						}
						else {
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-CLERK")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-CLERK")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-CLERK")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-CLERK")));
						}

						to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));
						to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

						try {
							EmailBean ebean = new EmailBean();

							ebean.setSubject(
									"Position Offer REJECTED - " + job.getCompetitionNumber() + ": " + job.getPositionTitle());
							for (Personnel p : to) {
								HashMap<String, Object> model = new HashMap<String, Object>();
								// set values to be used in template
								model.put("requesterName", p.getFullNameReverse());
								model.put("candidateName", profile.getFullNameReverse());
								model.put("userName", p.getUserName());
								model.put("recommendationId", rec.getRecommendationId());
								model.put("competitionNumber", job.getCompetitionNumber());
								model.put("competitionTitle", job.getPositionTitle());
								if (ass[0].getLocation() > 0) {
									model.put("location", ass[0].getLocationText());
								}
								else {
									model.put("location", "");
								}
								ebean.setTo(p.getEmailAddress());
								ebean.setBody(
										VelocityUtils.mergeTemplateIntoString("personnel/position_offer_rejected_admin.vm", model));
								ebean.setFrom("ms@nlesd.ca");
								ebean.send();
							}
						}
						catch (EmailException e) {
							e.printStackTrace();
						}

						rec = RecommendationManager.getTeacherRecommendationBean(rec.getRecommendationId());

						request.setAttribute("msg", "Your decline of the offer has been received.");
					}
				}

				request.setAttribute("RECOMMENDATION_BEAN", rec);
				JobOpportunityBean jbean = JobOpportunityManager.getJobOpportunityBean(rec.getCompetitionNumber());
				if(jbean.getIsSupport().equals("Y")){
					request.setAttribute("rth", RequestToHireManager.getRequestToHireByCompNum(rec.getJob().getCompetitionNumber()));
				
				}
				path = "applicant_offered_position.jsp";
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not add applicant profile ESD general experience.");
			path = "applicant_registration_step_4.jsp";
		}

		return path;
	}
}