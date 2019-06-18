package com.esdnl.survey.site.handler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveyResponseAnswerBean;
import com.esdnl.survey.bean.SurveyResponseBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.survey.bean.SurveySectionQuestionOptionBean;
import com.esdnl.survey.constant.SurveyQuestionTypeConstant;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.dao.SurveyResponseAnswerManager;
import com.esdnl.survey.dao.SurveyResponseManager;
import com.esdnl.survey.dao.SurveySectionQuestionManager;

public class ExportSurveyResponseAnswersRequestHandler extends RequestHandlerImpl {

	public ExportSurveyResponseAnswersRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				int id = form.getInt("id");

				SurveyBean survey = SurveyManager.getSuveryBean(id);

				if (survey != null) {
					Writer pw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\chris\\Desktop\\survey_responses_" + id
							+ ".csv"), "UTF-8");
					StringBuffer line = new StringBuffer();

					SurveySectionQuestionBean[] qList = SurveySectionQuestionManager.getSuverySectionQuestionBeans(survey);

					HashMap<Integer, SurveySectionQuestionBean> qMap = new HashMap<Integer, SurveySectionQuestionBean>();
					HashMap<Integer, HashMap<Integer, SurveySectionQuestionOptionBean>> qoMap = new HashMap<Integer, HashMap<Integer, SurveySectionQuestionOptionBean>>();

					//print header line.
					line.append("\"Response Date\"");
					for (SurveySectionQuestionBean ssqb : qList) {
						line.append(((line.length() > 0) ? "\t" : "") + "\"" + ssqb.getQuestionBody().replace("\r\n", " ") + "\"");

						if (ssqb.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_SINGLE_ANSWER)
								|| ssqb.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
							HashMap<Integer, SurveySectionQuestionOptionBean> oMap = new HashMap<Integer, SurveySectionQuestionOptionBean>();
							for (SurveySectionQuestionOptionBean opt : ssqb.getMultipleChoiceOptions()) {
								oMap.put(opt.getOptionId(), opt);
							}

							qoMap.put(ssqb.getQuestionId(), oMap);
						}

						qMap.put(ssqb.getQuestionId(), ssqb);
					}
					pw.write(line.toString() + "\n");

					HashMap<Integer, SurveySectionQuestionBean> qExported = null;
					HashMap<Integer, SurveyResponseAnswerBean> aMap = null;

					//print survey responses.
					SurveyResponseAnswerBean sra = null;
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					String answer;
					for (SurveyResponseBean sr : SurveyResponseManager.getSuveryResponseBeans(survey)) {
						qExported = new HashMap<Integer, SurveySectionQuestionBean>();

						aMap = SurveyResponseAnswerManager.getSurveyResponseAnswerBeanMap(sr);

						line = new StringBuffer();
						line.append("\"" + sdf.format(sr.getResponseDate()) + "\"");
						for (SurveySectionQuestionBean q : qList) {
							sra = aMap.get(q.getQuestionId());

							if (sra != null) {
								if (qExported.containsKey(sra.getQuestionId()))
									continue;

								q = qMap.get(sra.getQuestionId());
								if (q.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)
										|| q.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
									answer = "";
									int idx = 1;

									for (SurveyResponseAnswerBean srab : SurveyResponseAnswerManager.getSurveyResponseAnswerBeanList(sr,
											q)) {
										if (q.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_BULLETS)) {
											answer += (idx++) + ".: " + srab.getBody().replace("\r\n", ". ");
										}
										else if (q.getQuestionType().equals(SurveyQuestionTypeConstant.MULTIPLE_CHOICE_MULTIPLE_ANSWER)) {
											answer += (idx++)
													+ ".: "
													+ ((SurveySectionQuestionOptionBean) ((HashMap<Integer, SurveySectionQuestionOptionBean>) qoMap.get(srab.getQuestionId())).get(srab.getOptionId())).getOptionBody();
										}
									}
								}
								else if (sra.getOptionId() > 0) {
									answer = ((SurveySectionQuestionOptionBean) ((HashMap<Integer, SurveySectionQuestionOptionBean>) qoMap.get(sra.getQuestionId())).get(sra.getOptionId())).getOptionBody();
								}
								else {
									answer = sra.getBody().replace("\r\n", ". ");
								}

								line.append(((line.length() > 0) ? "\t" : "") + "\"" + answer + "\"");

								qExported.put(sra.getQuestionId(), null);
							}
							else {
								line.append("\t");
							}
						}
						pw.write(line.toString() + "\n");
					}

					pw.close();
				}
			}
		}
		catch (SurveyException e) {

		}

		return null;
	}
}
