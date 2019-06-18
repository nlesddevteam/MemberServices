package com.esdnl.survey.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;
import com.esdnl.survey.constant.SurveyQuestionTypeConstant;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.dao.SurveySectionManager;
import com.esdnl.survey.dao.SurveySectionQuestionManager;
import com.esdnl.survey.dao.SurveySectionQuestionOptionManager;
import com.esdnl.util.StringUtils;

public class CreateSectionQuestionRequestHandler extends RequestHandlerImpl {

	public CreateSectionQuestionRequestHandler() {

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
						new RequiredFormElement("option_count"), new RequiredSelectionFormElement("section_id", -1),
						new RequiredSelectionFormElement("question_type", -1), new RequiredFormElement("question_stem")
				});

				if (validate_form()) {
					SurveySectionBean section = SurveySectionManager.getSuverySectionBean(form.getInt("section_id"));

					if (section != null) {
						SurveySectionQuestionBean question = new SurveySectionQuestionBean();

						question.setSectionId(section.getSectionId());
						question.setQuestionBody(form.get("question_stem"));
						question.setQuestionType(SurveyQuestionTypeConstant.get(form.getInt("question_type")));
						if (form.exists("question_manditory"))
							question.setManditory(true);
						else
							question.setManditory(false);

						if (question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)
								|| question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
								|| question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {

							SurveySectionQuestionOptionBean option = null;
							int option_count = form.getInt("option_count");

							for (int i = 1; i <= option_count; i++) {
								if (!form.exists("option_" + i))
									continue;

								option = new SurveySectionQuestionOptionBean();
								option.setOptionBody(form.get("option_" + i));

								question.addMultipleChoiceOption(option);
							}

							if (form.exists("include_other")) {
								option = new SurveySectionQuestionOptionBean();
								option.setOptionBody("Other");
								option.setOther(true);

								question.addMultipleChoiceOption(option);
							}
						}
						else if (question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)) {
							question.setBullets(form.getInt("question_bullets"));
							question.setBulletLength(form.getInt("question_bullet_length"));
						}

						SurveySectionQuestionManager.addSurveySectionQuestionBean(question);

						request.setAttribute("SURVEY_BEAN", SurveyManager.getSuveryBean(section.getSurveyId()));
						request.setAttribute("SURVEY_SECTION_BEAN", section);

						request.setAttribute("msg", "Question created successfully.");

						path = "create_section_question.jsp";
					}
					else {
						request.setAttribute("msg", "Section with id=" + form.getInt("section_id") + "could not be found.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "index.jsp";
				}
			}
			else if (form.exists("op") && form.hasValue("op", "update")) {

				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("option_count"), new RequiredFormElement("question_id"),
						new RequiredSelectionFormElement("section_id", -1), new RequiredSelectionFormElement("question_type", -1),
						new RequiredFormElement("question_stem")
				});

				if (validate_form()) {
					SurveySectionQuestionBean question = SurveySectionQuestionManager.getSuverySectionQuestionBean(form.getInt("question_id"));
					SurveySectionBean section = SurveySectionManager.getSuverySectionBean(form.getInt("section_id"));

					if (question != null) {

						question.setSectionId(section.getSectionId());
						question.setQuestionBody(form.get("question_stem"));
						question.setQuestionType(SurveyQuestionTypeConstant.get(form.getInt("question_type")));
						if (form.exists("question_manditory"))
							question.setManditory(true);
						else
							question.setManditory(false);

						// remove existing question options.
						SurveySectionQuestionOptionManager.deleteSuverySectionQuestionOptionBeans(question);
						question.setOptions(null);

						if (question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)
								|| question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
								|| question.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {

							SurveySectionQuestionOptionBean option = null;
							int option_count = form.getInt("option_count");

							for (int i = 1; i <= option_count; i++) {
								if (!form.exists("option_" + i))
									continue;

								option = new SurveySectionQuestionOptionBean();
								option.setOptionBody(form.get("option_" + i));

								question.addMultipleChoiceOption(option);
							}

							if (form.exists("include_other")) {
								option = new SurveySectionQuestionOptionBean();
								option.setOptionBody("Other");
								option.setOther(true);

								question.addMultipleChoiceOption(option);
							}
						}

						SurveySectionQuestionManager.updateSurveySectionQuestionBean(question);

						request.setAttribute("SURVEY_BEAN", SurveyManager.getSuveryBean(section.getSurveyId()));
						request.setAttribute("SURVEY_SECTION_BEAN", section);

						request.setAttribute("msg", "Question updated successfully.");

						path = "create_section_question.jsp";
					}
					else {
						request.setAttribute("msg", "Section with id=" + form.getInt("section_id") + "could not be found.");

						path = "index.jsp";
					}
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "index.jsp";
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

						if (form.exists("section_id"))
							request.setAttribute("SURVEY_SECTION_BEAN",
									SurveySectionManager.getSuverySectionBean(form.getInt("section_id")));

						path = "create_section_question.jsp";
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