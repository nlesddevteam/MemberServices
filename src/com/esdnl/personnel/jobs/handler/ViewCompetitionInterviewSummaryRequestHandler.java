package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.personnel.jobs.dao.InterviewSummaryManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewCompetitionInterviewSummaryRequestHandler extends RequestHandlerImpl {

	public ViewCompetitionInterviewSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW","PERSONNEL-OTHER-MANAGER-VIEW"
		};

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
					request.setAttribute("summary", isb);
					request.setAttribute("guide", InterviewGuideManager.getInterviewGuideBean(isb.getCompetition()));

					request.setAttribute("comp_num_return", form.get("comp_num_return"));
					if(form.exists("vtype")) {
						request.setAttribute("hideview", "Y");
					}

					path = "admin_view_job_interview_summary.jsp";
				}
				else {
					request.setAttribute("msg", "Interview Summary could not be found");
					path = "admin_index.jsp";
				}

			}
			catch (JobOpportunityException e) {
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

}
