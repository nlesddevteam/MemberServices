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
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.util.StringUtils;

public class EditSurveyRequestHandler extends RequestHandlerImpl {

	public EditSurveyRequestHandler() {

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

				SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("id"));

				if (survey != null) {
					request.setAttribute("SURVEY_BEAN", survey);

					path = "create_survey.jsp";
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
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}
}
