package com.esdnl.survey.site.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.UploadFile;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.dao.SurveyManager;
import com.esdnl.survey.site.thread.SendSurveyInvitationThread;
import com.esdnl.util.StringUtils;

public class SendInvitationsRequestHandler extends RequestHandlerImpl {

	public SendInvitationsRequestHandler() {

		requiredPermissions = new String[] {
			"SURVEY-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.exists("op") && form.hasValue("op", "send")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("survey_id"), new RequiredFileFormElement("participant_list", "txt"),
						new RequiredFormElement("invitation_subject"), new RequiredFileFormElement("invitation_template", "txt")
				});

				if (validate_form()) {
					SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("survey_id"));

					if (survey != null) {

						String linefeed = Character.toString((char) 13) + Character.toString((char) 10);
						String line = null;
						UploadFile template = form.getUploadFile("invitation_template");
						BufferedReader br = new BufferedReader(new InputStreamReader(template.getInpuStream()));
						StringBuffer buf = new StringBuffer();
						while ((line = br.readLine()) != null) {
							if (StringUtils.isEmpty(line))
								buf.append(linefeed);
							else
								buf.append(line + linefeed);
						}
						br.close();

						UploadFile plist = form.getUploadFile("participant_list");
						ArrayList<String> participants = new ArrayList<String>();
						br = new BufferedReader(new InputStreamReader(plist.getInpuStream()));

						while ((line = br.readLine()) != null) {
							line = line.toLowerCase().trim();

							if (StringUtils.isEmpty(line))
								continue;

							participants.add(line);
						}
						br.close();

						EmailBean email_template = new EmailBean();

						email_template.setSubject(form.get("invitation_subject"));
						email_template.setBody(buf.toString());

						if (form.exists("from"))
							email_template.setFrom(form.get("from"));
						else
							email_template.setFrom("survey@nlesd.ca");

						email_template.setContentType(EmailBean.CONTENTTYPE_PLAIN);

						new SendSurveyInvitationThread(email_template, participants).start();

						request.setAttribute("SURVEY_BEAN", survey);
						request.setAttribute("msg", participants.size() + " invitation(s) queued to send.");

						path = "survey_invitation.jsp";
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
			else {
				validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("id")
				});

				if (validate_form()) {
					SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("id"));
					if (survey != null) {
						request.setAttribute("SURVEY_BEAN", survey);
						path = "survey_invitation.jsp";
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
