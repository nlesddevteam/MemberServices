package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.bean.InterviewGuideBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryScoreBean;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class EditCompetitionInterviewSummaryRequestHandler extends RequestHandlerImpl {

	public EditCompetitionInterviewSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Interview Summary ID is required.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		if (validate_form()) {
			try {
				InterviewSummaryBean summary = InterviewSummaryManager.getInterviewSummaryBean(form.getInt("id"));

				if (summary != null) {
					InterviewGuideBean guide = InterviewGuideManager.getInterviewGuideBean(summary.getCompetition());

					request.setAttribute("summary", summary);
					request.setAttribute("job", summary.getCompetition());
					request.setAttribute("profile", summary.getCandidate());
					request.setAttribute("guide", guide);

					if (form.exists("confirmed")) {
						summary.setStrengths(form.get("strengths"));
						summary.setGaps(form.get("gaps"));
						summary.setRecommendation(InterviewSummaryBean.SummaryRecommendation.get(form.getInt("recommendation")));

						summary.removeAllScores();

						InterviewSummaryScoreBean score = null;
						for (int i = 1; i <= 5; i++) {
							score = getInterviewSummaryScoreBean(i, guide);

							if (score != null) {
								summary.addInterviewSummaryScoreBean(score);
							}
						}

						InterviewSummaryManager.updateInterviewSummaryBean(summary);

						request.setAttribute("summary", summary);
						request.setAttribute("comp_num_return", summary.getCompetition().getCompetitionNumber());

						path = "admin_view_job_interview_summary.jsp";
					}
					else {
						path = "admin_edit_job_interview_summary.jsp";
					}
				}
				else {
					request.setAttribute("msg", "Interview Summary could not be found");
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
