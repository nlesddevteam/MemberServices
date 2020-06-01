package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ReopenCompetitionRequestHandler extends RequestHandlerImpl {

	public ReopenCompetitionRequestHandler() {

		requiredRoles = new String[] {
				"ADMINISTRATOR", "SEO - PERSONNEL"
		};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				//update the awarded date for comp to null
				JobOpportunityManager.reopenCompetition(form.get("comp_num"), usr.getPersonnel().getPersonnelID());
				//now get objects to send back to bpage
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));
				TeacherRecommendationBean[] rec = RecommendationManager.getTeacherRecommendationBean(form.get("comp_num"));
				request.setAttribute("msg", "Competition has been reopened");
				request.setAttribute("comp", job);
				request.setAttribute("recs", rec);
				path = "admin_view_job_recommendation_list.jsp";
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not reopen competition");
				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
		}

		return path;
	}
}
