package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.servlet.BypassLoginRequestHandlerImpl;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteTeacherRecommendationRequestHandler extends BypassLoginRequestHandlerImpl {

	public DeleteTeacherRecommendationRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-DELETE-RECOMMENDATION"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_view_job_recommendation_list.jsp";

		if (validate_form()) {
			try {
				TeacherRecommendationBean rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				if (rec != null) {
					RecommendationManager.deleteTeacherRecommendationBean(form.getInt("id"));

					path += "?comp_num=" + rec.getCompetitionNumber();
				}
				else
					path = "index.jsp";

			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);

				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Could not view reference.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
		}

		return path;
	}
}