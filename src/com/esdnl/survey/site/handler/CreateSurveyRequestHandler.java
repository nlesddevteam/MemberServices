package com.esdnl.survey.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.util.StringUtils;

public class CreateSurveyRequestHandler extends RequestHandlerImpl {

	public CreateSurveyRequestHandler() {

		requiredPermissions = new String[] {
			"SURVEY-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.exists("op") && form.hasValue("op", "create")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("survey_name"), new RequiredFormElement("survey_start_date"),
						new RequiredPatternFormElement("survey_start_date", FormElementPattern.DATE_PATTERN)
				});

				if (validate_form()) {
					if (form.exists("survey_password") && !form.valuesMatch("survey_password", "survey_password_confirm")) {
						request.setAttribute("FORM", form);
						request.setAttribute("msg", "Confirmation password does not match.");

						path = "create_survey.jsp";
					}
					else {

						SurveyBean survey = new SurveyBean();

						survey.setName(form.get("survey_name"));
						survey.setPassword(form.get("survey_password"));
						survey.setStartDate(form.getDate("survey_start_date"));

						if (form.exists("survey_end_date"))
							survey.setEndDate(form.getDate("survey_end_date"));

						if (form.exists("survey_introduction"))
							survey.setIntroduction(form.get("survey_introduction"));

						if (form.exists("survey_thankyou"))
							survey.setThankYouMessage(form.get("survey_thankyou"));

						if (form.exists("survey_internal"))
							survey.setInternal(true);
						else
							survey.setInternal(false);

						SurveyManager.addSurveyBean(survey);

						request.setAttribute("msg", "Survey added successfully.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "create_survey.jsp";
				}
			}
			else if (form.exists("op") && form.hasValue("op", "update")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("survey_name"), new RequiredFormElement("survey_start_date"),
						new RequiredPatternFormElement("survey_start_date", FormElementPattern.DATE_PATTERN)
				});

				if (validate_form()) {
					if (form.exists("survey_password") && !form.valuesMatch("survey_password", "survey_password_confirm")) {
						request.setAttribute("FORM", form);
						request.setAttribute("msg", "Confirmation password does not match.");

						path = "create_survey.jsp";
					}
					else {

						SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("survey_id"));

						survey.setName(form.get("survey_name"));
						survey.setPassword(form.get("survey_password"));
						survey.setStartDate(form.getDate("survey_start_date"));
						if (form.exists("survey_end_date"))
							survey.setEndDate(form.getDate("survey_end_date"));
						else
							survey.setEndDate(null);

						if (form.exists("survey_introduction"))
							survey.setIntroduction(form.get("survey_introduction"));
						else
							survey.setIntroduction(null);

						if (form.exists("survey_thankyou"))
							survey.setThankYouMessage(form.get("survey_thankyou"));
						else
							survey.setThankYouMessage(null);

						if (form.exists("survey_internal"))
							survey.setInternal(true);
						else
							survey.setInternal(false);

						SurveyManager.updateSurveyBean(survey);

						request.setAttribute("msg", "Survey updated successfully.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "create_survey.jsp";
				}
			}
			else
				path = "create_survey.jsp";
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}
}