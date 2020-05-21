package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ApplicantViewCompetitionInterviewSummaryRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public ApplicantViewCompetitionInterviewSummaryRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		if (validate_form()) {
			try {
				InterviewSummaryBean isb = InterviewSummaryManager.getInterviewSummaryBean(form.getInt("id"));

				if (isb != null) {
					if (StringUtils.equals(isb.getCandidate().getUID(), profile.getUID())) {
						request.setAttribute("summary", isb);
						request.setAttribute("guide", InterviewGuideManager.getInterviewGuideBean(isb.getCompetition()));

						path = "applicant_view_job_interview_summary.jsp";
					}
					else {
						request.setAttribute("msg",
								"ILLEGAL ATTEMPT to access another candidate's interview summary DETECTED, LOGGED and REPORTED.");
						path = "view_applicant.jsp";
					}
				}
				else {
					request.setAttribute("msg", "Interview Summary could not be found");
					path = "view_applicant.jsp";
				}

			}
			catch (JobOpportunityException e) {
				request.setAttribute("msg", e.getMessage());
				path = "view_applicant.js";
			}

		}
		else {
			request.setAttribute("msg", validator.getErrorString());
			path = "view_applicant.js";
		}

		return path;
	}

}
