package com.esdnl.survey.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.dao.SurveyAnalysisManager;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.dao.SurveySectionManager;
import com.esdnl.survey.dao.SurveySectionQuestionManager;
import com.esdnl.util.StringUtils;

public class ViewSurveySectionQuestionHistogramRequestHandler extends RequestHandlerImpl {

	public ViewSurveySectionQuestionHistogramRequestHandler() {

		requiredPermissions = new String[] {
			"SURVEY-ADMIN-VIEW"
		};

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

				SurveySectionQuestionBean question = SurveySectionQuestionManager.getSuverySectionQuestionBean(form.getInt("id"));

				if (question != null) {
					SurveySectionBean section = SurveySectionManager.getSuverySectionBean(question.getSectionId());

					request.setAttribute("SURVEY_SECTION_QUESTION", question);
					request.setAttribute("SURVEY_SECTION_BEAN", section);
					request.setAttribute("SURVEY_BEAN", SurveyManager.getSuveryBean(section.getSurveyId()));

					request.setAttribute("HISTOGRAM", SurveyAnalysisManager.getHistogram(question));

					path = "survey_section_question_histogram_analysis.jsp";
				}
				else {
					request.setAttribute("msg", "SurveySectionQuestion with id=" + form.getInt("id") + "could not be found.");

					path = "index.jsp";
				}
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

				path = "index.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}
}
