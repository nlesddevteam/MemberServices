package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.GradeSubjectPercentUnitBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceListBean;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.PositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;
import com.esdnl.personnel.jobs.dao.ApplicantEsdExperienceManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceListManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddTeacherRecommendationRequestHandler extends RequestHandlerImpl {

	public AddTeacherRecommendationRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	@SuppressWarnings("unchecked")
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (!StringUtils.isEmpty(form.get("op"))) {

				if (form.get("op").equals("CANDIDATE_DETAILS")) { // AJAX CALL
					String sin = request.getParameter("sin");
					ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(sin);
					ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(sin);
					ApplicantEducationOtherBean other = ApplicantEducationOtherManager.getApplicantEducationOtherBean(sin);
					//ReferenceBean[] refs = ReferenceManager.getReferenceBeans(profile);
					NLESDReferenceListBean[] refs = NLESDReferenceListManager.getReferenceBeansByApplicant(sin);
					JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
					Collection<InterviewSummaryBean> interviews = InterviewSummaryManager.getInterviewSummaryBeans(profile);
					Collection<ReferenceCheckRequestBean> ref_chks = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(
							job, profile);

					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<APPLICANT>");
					sb.append(profile.generateXML());
					if (esd_exp != null)
						sb.append(esd_exp.generateXML());
					if (other != null)
						sb.append(other.generateXML());
					sb.append("<REFERENCE-CHECK-REQUESTS>");
					for (ReferenceCheckRequestBean ref_chk : ref_chks)
						sb.append(ref_chk.toXML());
					sb.append("</REFERENCE-CHECK-REQUESTS>");
					sb.append("<REFERENCES>");
					//for (ReferenceBean ref : refs)
					for (NLESDReferenceListBean ref : refs)
						sb.append(ref.toXML());
					sb.append("</REFERENCES>");
					if (interviews != null) {
						sb.append("<JOB-INTERVIEW-SUMMARIES>");
						for (InterviewSummaryBean interview : interviews) {
							sb.append(interview.toXML());
						}
						sb.append("</JOB-INTERVIEW-SUMMARIES>");
					}
					sb.append("</APPLICANT>");
					xml = sb.toString().replaceAll("&", "&amp;");

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");

					out.write(xml);
					out.flush();
					out.close();
					path = null;
				}
				else {
					validator = new FormValidator(new FormElement[] {
							new RequiredFormElement("comp_num"), new RequiredFormElement("candidate_name"),
							new RequiredPatternFormElement("dob", FormElementPattern.DATE_PATTERN),
							new RequiredSelectionFormElement("position", -1),
							new RequiredSelectionFormElement("recommended_status", -1), 
							new RequiredFormElement("References_Satisfactory"), new RequiredFormElement("Special_Conditions")
					});

					
					if (validate_form()) {
						try {
							JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");

							HashMap<String, ArrayList<GradeSubjectPercentUnitBean>> all_jobs_gsu = (HashMap<String, ArrayList<GradeSubjectPercentUnitBean>>) session.getAttribute(
									"ALL_JOBS_GSU_BEANS");
							ArrayList<GradeSubjectPercentUnitBean> gsu_beans = null;
							if(job.getIsSupport().equals("N")){	
								//new RequiredFormElement("reference_id"),
								//new RequiredFormElement("interview_summary_id"),
								//new RequiredFormElement("Interview_Panel"),
								//form validation moved here since support staff might not have them
								ApplicantProfileBean pbean = ApplicantProfileManager.getApplicantProfileBean(form.get("candidate_name"));
								if(!(pbean.isProfileVerified())){
									request.setAttribute("msg", "Please verify candidate's profile.");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
								if(form.getInt("reference_id") <= 0) {
									request.setAttribute("msg", "Please select reference.");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
								if(form.getInt("interview_summary_id") <= 0) {
									request.setAttribute("msg", "Please select interview summary.");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
								if(form.get("Interview_Panel") == null) {
									request.setAttribute("msg", "Please add interview panel.");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
								if (all_jobs_gsu != null)
									gsu_beans = (ArrayList<GradeSubjectPercentUnitBean>) all_jobs_gsu.get(job.getCompetitionNumber());
			
								if ((gsu_beans == null) || (gsu_beans.size() <= 0)) {
									request.setAttribute("msg", "Please indicate position breakdown (SECTION 2).");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
								else if (!positionUnitCheck()) {
									request.setAttribute("msg",
											"Please ensure that the position breakdown percentage total matches the position unit allocation.");
									request.setAttribute("FORM", form);
			
									path = "admin_job_teacher_recommendation.jsp";
									return path;
								}
							}
							TeacherRecommendationBean bean = new TeacherRecommendationBean();
							//check for new no ref/summary checkbox
							if(job.getIsSupport().equals("Y")){	
								if(form.get("chknoref") == null) {
									bean.setReferenceId(form.getInt("reference_id"));
									bean.setInterviewSummaryId(form.getInt("interview_summary_id"));
									if(form.get("Interview_Panel") ==  "") {
										bean.setInterviewPanel("N/A");
									}else {
										bean.setInterviewPanel(form.get("Interview_Panel"));
									}
									
								}else {
									bean.setReferenceId(-1);
									bean.setInterviewSummaryId(-1);
									bean.setInterviewPanel("N/A");
								}
							}else {
								bean.setReferenceId(form.getInt("reference_id"));
								bean.setInterviewSummaryId(form.getInt("interview_summary_id"));
								bean.setInterviewPanel(form.get("Interview_Panel"));
							}
							bean.setCandidate2(form.get("candidate_2"));
							bean.setCandidate3(form.get("candidate_3"));
							bean.setCandidateId(form.get("candidate_name"));
							bean.setCompetitionNumber(job.getCompetitionNumber());
							bean.setCurrentStatus(RecommendationStatus.RECOMMENDED);
							bean.setEmploymentStatus(EmploymentConstant.get(form.getInt("recommended_status")));
							
							bean.setOtherComments(form.get("Other_Comments"));
							if (job.getIsSupport().equals("N")) {
								bean.setPositionType(PositionTypeConstant.get(form.getInt("position")));
							}
							else {
								bean.setRth_position_type(RTHPositionTypeConstant.get(form.getInt("position")));
							}
							bean.setPositionTypeOther(form.get("position_other"));
							bean.setRecommendBy(usr.getPersonnel().getPersonnelID());
							bean.setReferencesSatisfactory(form.get("References_Satisfactory"));
							bean.setSpecialConditions(form.get("Special_Conditions"));
							bean.setSpecialConditionsComment(form.get("Special_Conditions_Comment"));
							bean.setCandidateComments(form.get("rec_cand_comments"));
							bean.setCandidateComments2(form.get("rec_cand_comments2"));
							bean.setCandidateComments3(form.get("rec_cand_comments3"));
							if (job.getIsSupport().equals("N")) {
								all_jobs_gsu = (HashMap<String, ArrayList<GradeSubjectPercentUnitBean>>) session.getAttribute(
										"ALL_JOBS_GSU_BEANS");
								gsu_beans = null;

								if (all_jobs_gsu != null) {
									gsu_beans = (ArrayList<GradeSubjectPercentUnitBean>) all_jobs_gsu.get(job.getCompetitionNumber());
									if (gsu_beans != null)
										bean.setGSU((GradeSubjectPercentUnitBean[]) gsu_beans.toArray(new GradeSubjectPercentUnitBean[0]));
								}
							}

							bean = RecommendationManager.addTeacherRecommendationBean(bean);

							this.sendRecommendationNotification(bean);

							request.setAttribute("RECOMMENDATION_BEAN",
									RecommendationManager.getTeacherRecommendationBean(bean.getRecommendationId()));
							request.setAttribute("msg", "Recommendation submitted successfully.");

							path = "admin_view_job_teacher_recommendation.jsp";
						}
						catch (JobOpportunityException e) {
							e.printStackTrace();
							request.setAttribute("msg", "Could not add reference.");
							request.setAttribute("FORM", form);

							path = "admin_job_teacher_recommendation.jsp";
						}
					}
					else {
						request.setAttribute("FORM", form);

						request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

						path = "admin_job_teacher_recommendation.jsp";
					}
				}
			}
			else {
				if (session.getAttribute("JOB") == null && request.getParameter("comp_num") != null) {
					JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(request.getParameter("comp_num"));
					session.setAttribute("JOB", job);
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(job));
				}

				path = "admin_job_teacher_recommendation.jsp";
			}
		}
		catch (Exception e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ee) {}

			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "admin_job_teacher_recommendation.jsp";
		}

		return path;
	}

	@SuppressWarnings("unchecked")
	private boolean positionUnitCheck() {

		boolean check = false;
		double gsu_percent = 0.0;

		try {
			JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
			AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());

			HashMap<String, ArrayList<GradeSubjectPercentUnitBean>> all_jobs_gsu = (HashMap<String, ArrayList<GradeSubjectPercentUnitBean>>) session.getAttribute(
					"ALL_JOBS_GSU_BEANS");
			ArrayList<GradeSubjectPercentUnitBean> gsu_beans = null;

			if (all_jobs_gsu != null)
				gsu_beans = (ArrayList<GradeSubjectPercentUnitBean>) all_jobs_gsu.get(job.getCompetitionNumber());

			if (gsu_beans != null) {
				GradeSubjectPercentUnitBean[] beans = (GradeSubjectPercentUnitBean[]) gsu_beans.toArray(
						new GradeSubjectPercentUnitBean[0]);
				for (int i = 0; i < beans.length; i++)
					gsu_percent += beans[i].getUnitPercentage();
			}

			check = (gsu_percent == ad.getUnits());
		}
		catch (JobOpportunityException e) {
			check = false;
		}

		return check;
	}

	private void sendRecommendationNotification(TeacherRecommendationBean rec) {

		try {
			ArrayList<Personnel> sendTo = new ArrayList<Personnel>();

			JobOpportunityBean job = rec.getJob();
			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

			/*
			 * June 12, 2015 - Chris
			 * Removing staffing assignments, and Programs SEO from approval notifications.
			 * 
			 */

			/*
			ArrayList<SEOStaffingAssignmentBean> staffing = SEOStaffingAssignmentManager.getSEOStaffingAssignmentBeans(job);
			
			if (staffing.size() > 0) {
				for (SEOStaffingAssignmentBean a : staffing)
					sendTo.add(a.getPersonnel());
			}
			else if (ass[0].getLocation() > 0) {
				SchoolFamily sf = null;
				try {
					sf = SchoolFamilyDB.getSchoolFamily(SchoolDB.getSchool(ass[0].getLocation()));
			
					sendTo.add(sf.getProgramSpecialist());
				}
				catch (SchoolFamilyException e) {
					EmailBean alert = new EmailBean();
					alert.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR "));
					alert.setSubject("SchoolFamilyException: No family set for location id[" + ass[0].getLocation() + "]");
			
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					alert.setBody(sw.toString());
			
					alert.send();
				}
				catch (NullPointerException e) {
					EmailBean alert = new EmailBean();
					alert.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR "));
					alert.setSubject("SchoolFamilyException: No SEO set for location id[" + ass[0].getLocation() + "], family["
							+ sf.getSchoolFamilyName() + "]");
			
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					alert.setBody(sw.toString());
			
					alert.send();
				}
			}
			 */
			//check to see what type of position it is ADMIN/LEADERSHIP should goto director
			
				if (job.getJobType().equals(JobTypeConstant.ADMINISTRATIVE)
						|| job.getJobType().equals(JobTypeConstant.LEADERSHIP)) {
					Personnel admins[] = PersonnelDB.getPersonnelByRole("ASSOCIATE ASSISTANT DIRECTOR");
					for (int i = 0; i < admins.length; i++) {
						sendTo.add(admins[i]);
					}
				}
				else {
					if (sendTo.size() <= 0) {
						if (ass[0].getLocation() > 0) {//send to SEO for Region
							NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass[0].getLocationZone().getZoneId());

							for (int j = 0; j < mh.getRegionalHRAdmins().length; j++) {
								sendTo.add(mh.getRegionalHRAdmins()[j]);
							}
						}
						else {
							for (Personnel p : PersonnelDB.getPersonnelByRole("SEO - PERSONNEL")) {
								sendTo.add(p);
							}
						}
					}

					// send email HR ADE
					Personnel admins[] = PersonnelDB.getPersonnelByRole("AD HR");
					for (int i = 0; i < admins.length; i++) {
						sendTo.add(admins[i]);
					}

					sendTo.remove(rec.getRecommendedByPersonnel());

					for (Personnel p : PersonnelDB.getPersonnelByRole("ADMINISTRATOR")) {
						sendTo.add(p);
					}
				}
				
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
}