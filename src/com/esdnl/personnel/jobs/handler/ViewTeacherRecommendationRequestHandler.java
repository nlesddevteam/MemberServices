package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.NLESDRecommendationTrackingFormManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewTeacherRecommendationRequestHandler extends RequestHandlerImpl {

	public ViewTeacherRecommendationRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW","RTH-VIEW-SHORTLIST"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id")
		});

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_view_job_teacher_recommendation.jsp";

		if (validate_form()) {
			try {
				TeacherRecommendationBean rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				if (rec != null) {
					JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(rec.getCompetitionNumber());

					ApplicantProfileBean[] shortlist = ApplicantProfileManager.getApplicantShortlistExcludingInterviewDeclines(
							job);

					//retrieve the recommendation tracking form information, right now max three candidates on screen
					int sidx = 0;
					//adjust array to account for the declined interviews that is causing array out of bounds
					String[][] test = new String[shortlist.length + 3][2];

					//primary candidate
					test[0][0] = rec.getCandidateId();
					test[0][1] = rec.getCandidateComments();

					if (StringUtils.isNotBlank(rec.getCandidate2()) && !StringUtils.equals(rec.getCandidate2(), "-1")) {
						test[1][0] = rec.getCandidate2();
						test[1][1] = rec.getCandidateComments2();

						sidx = 1;
					}
					else {
						test[1][0] = "None Selected";
						test[1][1] = "None Selected";
						sidx = 1;
					}

					if (StringUtils.isNotBlank(rec.getCandidate3()) && !StringUtils.equals(rec.getCandidate3(), "-1")) {
						test[2][0] = rec.getCandidate3();
						test[2][1] = rec.getCandidateComments3();

						sidx = 2;
					}
					else {
						test[2][0] = "None Selected";
						test[2][1] = "None Selected";
						sidx = 2;
					}

					for (ApplicantProfileBean profile : shortlist) {
						if (!isRecommendedCandidate(rec, profile)) {
							if (sidx + 1 < test.length) {
								test[++sidx][0] = profile.getUID();
							}
						}
					}

					request.setAttribute("TFORM", NLESDRecommendationTrackingFormManager.getNLESDTrackingFormList(test, rec));
					request.setAttribute("RECOMMENDATION_BEAN", rec);
					request.setAttribute("JOB_SHORTLIST_INTERVIEW_DECLINES",
							ApplicantProfileManager.getApplicantShortlistInterviewDeclines(job));

					session.setAttribute("JOB", job);
					session.setAttribute("JOB_SHORTLIST", ApplicantProfileManager.getApplicantShortlist(job));
				}
				else {
					request.setAttribute("msg", "Invalid request.");

					path = "admin_index.jsp";
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);

				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Could not view reference.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
		}

		return path;
	}

	private boolean isRecommendedCandidate(TeacherRecommendationBean rec, ApplicantProfileBean profile) {

		boolean recommended = false;

		String applicantId = profile.getUID();

		if (!StringUtils.equals(applicantId, "-1") && (applicantId.equals(rec.getCandidateId())
				|| applicantId.equals(rec.getCandidate2()) || applicantId.equals(rec.getCandidate3()))) {
			recommended = true;
		}

		return recommended;

	}
}