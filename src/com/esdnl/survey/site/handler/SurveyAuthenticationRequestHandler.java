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
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.util.StringUtils;

public class SurveyAuthenticationRequestHandler extends PublicAccessRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("survey_id"), new RequiredFormElement("survey_password")
			});

			if (validate_form()) {
				SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("survey_id"));

				if (survey != null) {
					if (survey.getPassword().equals(form.get("survey_password"))) {
						path = "take_survey.jsp";

						session.setMaxInactiveInterval(7200);
					}
					else {
						request.setAttribute("msg", "Password is incorrect! Please try again.");
						path = "survey_password.jsp";
					}
					request.setAttribute("SURVEY_BEAN", survey);
				}
				else {
					request.setAttribute("msg", "Survey with id=" + form.getInt("id") + "could not be found.");

					path = "index.jsp";
				}
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

				if (form.exists("survey_id")) {
					request.setAttribute("SURVEY_BEAN", SurveyManager.getSuveryBean(form.getInt("survey_id")));
					path = "survey_password.jsp";
				}
				else
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
