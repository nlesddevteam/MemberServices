package com.esdnl.survey.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.dao.SurveySectionManager;
import com.esdnl.util.StringUtils;

public class CreateSurveySectionRequestHandler extends RequestHandlerImpl {

	public CreateSurveySectionRequestHandler() {

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
						new RequiredFormElement("survey_id"), new RequiredFormElement("section_heading")
				});

				if (validate_form()) {
					SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("survey_id"));

					if (survey != null) {
						SurveySectionBean section = new SurveySectionBean();

						section.setSurveyId(survey.getSurveyId());
						section.setHeading(form.get("section_heading"));

						if (form.exists("survey_heading_displayed"))
							section.setHeaderDisplayed(true);

						if (form.exists("section_introduction"))
							section.setIntroduction(form.get("section_introduction"));

						if (form.exists("section_instructions"))
							section.setInstructions(form.get("section_instructions"));

						SurveySectionManager.addSurveySectionBean(section);

						request.setAttribute("SURVEY_BEAN", survey);
						request.setAttribute("SURVEY_SECTION_BEANS", SurveySectionManager.getSuverySectionBeans(survey));

						request.setAttribute("msg", "Section added successfully.");

						path = "survey_sections.jsp";
					}
					else {
						request.setAttribute("msg", "Survey with id=" + form.getInt("id") + "could not be found.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "create_survey_section.jsp";
				}
			}
			else if (form.exists("op") && form.hasValue("op", "update")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("section_id"), new RequiredFormElement("section_heading")
				});

				if (validate_form()) {
					SurveySectionBean section = SurveySectionManager.getSuverySectionBean(form.getInt("section_id"));

					if (section != null) {

						section.setHeading(form.get("section_heading"));

						if (form.exists("survey_heading_displayed"))
							section.setHeaderDisplayed(true);
						else
							section.setHeaderDisplayed(false);

						if (form.exists("section_introduction"))
							section.setIntroduction(form.get("section_introduction"));
						else
							section.setIntroduction(null);

						if (form.exists("section_instructions"))
							section.setInstructions(form.get("section_instructions"));
						else
							section.setInstructions(null);

						SurveySectionManager.updateSurveySectionBean(section);

						SurveyBean survey = SurveyManager.getSuveryBean(section.getSurveyId());

						request.setAttribute("SURVEY_BEAN", survey);
						request.setAttribute("SURVEY_SECTION_BEANS", SurveySectionManager.getSuverySectionBeans(survey));

						request.setAttribute("msg", "Section updated successfully.");

						path = "survey_sections.jsp";
					}
					else {
						request.setAttribute("msg", "Survey Section with id=" + form.getInt("id") + "could not be found.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "create_survey_section.jsp";
				}
			}
			else {
				validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("id")
				});

				if (validate_form()) {
					SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("id"));
					if (survey != null) {
						request.setAttribute("SURVEY_BEAN", survey);
						path = "create_survey_section.jsp";
					}
					else {
						request.setAttribute("msg", "Survey with id=" + form.getInt("id") + "could not be found.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "index.jsp";
				}
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