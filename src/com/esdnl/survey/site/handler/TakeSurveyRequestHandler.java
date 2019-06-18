package com.esdnl.survey.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyResponseAnswerBean;
import com.esdnl.survey.bean.SurveyResponseBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;
import com.esdnl.survey.constant.SurveyQuestionTypeConstant;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.dao.SurveyResponseManager;
import com.esdnl.survey.dao.SurveySectionQuestionManager;
import com.esdnl.survey.dao.SurveySectionQuestionOptionManager;
import com.esdnl.util.StringUtils;

public class TakeSurveyRequestHandler extends PublicAccessRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.exists("op") && form.hasValue("op", "create")) {

				validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("survey_id")
				});

				if (validate_form()) {
					SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("survey_id"));

					if (survey != null) {
						SurveySectionQuestionBean[] questions = SurveySectionQuestionManager.getSuverySectionQuestionBeans(survey);

						validator = new FormValidator();
						for (int i = 0; i < questions.length; i++) {
							if (questions[i].isManditory()
									&& !questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {

								if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)) {
									validator.addFormElement(new RequiredFormElement("q_" + questions[i].getQuestionId() + "_0"));
								}
								else if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.LONG_ANSWER)
										|| questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.SHORT_ANSWER)
										|| questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)
										|| questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.TRUE_OR_FALSE)) {
									validator.addFormElement(new RequiredFormElement("q_" + questions[i].getQuestionId()));
								}
								else if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {
									SurveySectionQuestionOptionBean[] options = SurveySectionQuestionOptionManager.getSuverySectionQuestionOptionBeans(questions[i]);
									for (int o = 0; o < options.length; o++) {
										validator.addFormElement(new RequiredFormElement("q_" + questions[i].getQuestionId() + "_"
												+ options[o].getOptionId()));
									}
								}
							}
						}

						if (validate_form()) {

							SurveyResponseBean rBean = new SurveyResponseBean();

							rBean.setSurveyId(survey.getSurveyId());
							rBean.setIpAddress(request.getRemoteAddr());

							SurveyResponseAnswerBean answer = null;

							for (int i = 0; i < questions.length; i++) {

								if (!(questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)
										|| questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS) || questions[i].getQuestionType().equals(
										SurveyQuestionTypeConstant.MULTIPLE_BULLETS))) {

									if (!questions[i].isManditory() && !form.exists("q_" + questions[i].getQuestionId()))
										continue;

									answer = new SurveyResponseAnswerBean();
									answer.setQuestionId(questions[i].getQuestionId());

									if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.LONG_ANSWER)
											|| questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.SHORT_ANSWER)) {
										answer.setBody(form.get("q_" + questions[i].getQuestionId()));
									}
									else if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.TRUE_OR_FALSE)) {
										answer.setTrueOrFalse(form.getBoolean("q_" + questions[i].getQuestionId()));
									}
									else if (questions[i].getQuestionType().equals(
											SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)) {
										answer.setOptionId(form.getInt("q_" + questions[i].getQuestionId()));
										if (form.exists("q_" + questions[i].getQuestionId() + "_other")) {
											answer.setBody(form.get("q_" + questions[i].getQuestionId() + "_other"));
										}
									}

									rBean.addAnswer(answer);
								}
								else if (questions[i].getQuestionType().equals(
										SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
									SurveySectionQuestionOptionBean[] options = questions[i].getMultipleChoiceOptions();
									for (int o = 0; o < options.length; o++) {
										if (!form.exists("q_" + questions[i].getQuestionId() + "_" + options[o].getOptionId()))
											continue;

										answer = new SurveyResponseAnswerBean();
										answer.setQuestionId(questions[i].getQuestionId());
										answer.setOptionId(options[o].getOptionId());

										rBean.addAnswer(answer);
									}
								}
								else if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_RATINGS)) {
									SurveySectionQuestionOptionBean[] options = questions[i].getMultipleChoiceOptions();
									for (int o = 0; o < options.length; o++) {

										if (!questions[i].isManditory()
												&& !form.exists("q_" + questions[i].getQuestionId() + "_" + options[o].getOptionId()))
											continue;

										answer = new SurveyResponseAnswerBean();
										answer.setQuestionId(questions[i].getQuestionId());
										answer.setOptionId(options[o].getOptionId());
										answer.setRating(form.getInt("q_" + questions[i].getQuestionId() + "_" + options[o].getOptionId()));

										rBean.addAnswer(answer);
									}
								}
								else if (questions[i].getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)) {
									for (int o = 0; o < questions[i].getBullets(); o++) {
										if (!form.exists("q_" + questions[i].getQuestionId() + "_" + o))
											continue;

										answer = new SurveyResponseAnswerBean();
										answer.setQuestionId(questions[i].getQuestionId());
										answer.setBody(form.get("q_" + questions[i].getQuestionId() + "_" + o));

										rBean.addAnswer(answer);
									}
								}
							}

							SurveyResponseManager.addSurveyResponseBean(rBean);

							if (!StringUtils.isEmpty(survey.getThankYouMessage()))
								request.setAttribute("msg", survey.getThankYouMessage());
							else
								request.setAttribute("msg", "Thank you for completeing the survey!");

							path = "index.jsp";
						}
						else {
							request.setAttribute("SURVEY_BEAN", survey);

							request.setAttribute("FORM", form);

							request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

							path = "take_survey.jsp";
						}
					}
					else {
						request.setAttribute("msg", "Survey with id=" + form.getInt("survey_id") + "could not be found.");

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

						if (survey.hasPassword()) {
							if ((request.getParameter("p") != null) && request.getParameter("p").equals(survey.getPassword())) {
								path = "take_survey.jsp";
							}
							else {
								path = "survey_password.jsp";
							}
						}
						else
							path = "take_survey.jsp";
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

		session.setMaxInactiveInterval(7200);

		return path;
	}
}
