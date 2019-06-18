package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.InterviewGuideBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryScoreBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddCompetitionInterviewSummaryRequestHandler extends RequestHandlerImpl {

	public AddCompetitionInterviewSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicant_id"), new RequiredFormElement("comp_num")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		if (validate_form()) {
			try {
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id"));
				InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(job);

				if ((job != null) && (profile != null) && (guide != null)) {
					request.setAttribute("job", job);
					request.setAttribute("profile", profile);
					request.setAttribute("guide", guide);

					if (form.exists("confirmed")) {
						InterviewSummaryBean summary = new InterviewSummaryBean();

						summary.setCandidate(profile);
						summary.setCompetition(job);
						summary.setStrengths(form.get("strengths"));
						summary.setGaps(form.get("gaps"));
						summary.setRecommendation(InterviewSummaryBean.SummaryRecommendation.get(form.getInt("recommendation")));

						InterviewSummaryScoreBean score = null;
						for (int i = 1; i <= 5; i++) {
							score = getInterviewSummaryScoreBean(i, guide);

							if (score != null) {
								summary.addInterviewSummaryScoreBean(score);
							}
						}

						InterviewSummaryManager.addInterviewSummaryBean(summary);

						request.setAttribute("summary", summary);

						if (!job.getJobType().equal(JobTypeConstant.POOL)) {
							request.setAttribute("comp_num_return", job.getCompetitionNumber());
						}

						path = "admin_view_job_interview_summary.jsp";
					}
					else {
						path = "admin_add_job_interview_summary.jsp";
					}
				}
				else {
					request.setAttribute("msg",
							"JobOpportunity and/or ApplicantProfile and/or Interview Guide could not be found");
					path = "admin_index.jsp";
				}

			}
			catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
			path = "admin_index.jsp";
		}

		return path;
	}

	private InterviewSummaryScoreBean getInterviewSummaryScoreBean(int id, InterviewGuideBean guide)
			throws SecurityException,
				NoSuchMethodException,
				IllegalArgumentException,
				NullPointerException,
				IllegalAccessException,
				InvocationTargetException {

		InterviewSummaryScoreBean score = null;
		Method method = null;

		if (form.exists("interviewer" + id)) {
			score = new InterviewSummaryScoreBean();

			score.setInterviewer(form.get("interviewer" + id));

			for (int i = 1; i <= guide.getQuestionCount(); i++) {
				method = score.getClass().getDeclaredMethod("setScore" + i, double.class);
				if (form.exists("q" + i + "_" + id) && StringUtils.isNotEmpty(form.get("q" + i + "_" + id))) {
					method.invoke(score, new Double(form.getDouble("q" + i + "_" + id)));
				}
				else {
					method.invoke(score, new Double(0));
				}
			}
		}

		return score;
	}

}
