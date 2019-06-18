package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.bean.InterviewGuideBean;
import com.esdnl.personnel.jobs.bean.InterviewGuideQuestionBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddInterviewGuideRequestHandler extends RequestHandlerImpl {

	public AddInterviewGuideRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("title", "Guide title is required."),
				new RequiredFormElement("scalebottom", "Rating scale bottom value is required."),
				new RequiredFormElement("scaletop", "Rating scale top value is required.")
		});

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			if (form.exists("confirmed")) {

				if (validate_form()) {
					InterviewGuideBean guide = new InterviewGuideBean();

					guide.setTitle(form.get("title"));
					guide.setRatingScaleBottom(form.getInt("scalebottom"));
					guide.setRatingScaleTop(form.getInt("scaletop"));
					guide.setSchoolYear(form.get("lst_schoolyear"));
					guide.setGuideType(form.get("guide_type"));
					if (!StringUtils.isEmpty(form.get("activelist")))
						guide.setActiveList(true);
					
					if (StringUtils.isNotEmpty(form.get("q1_weight")) && StringUtils.isNotEmpty(form.get("q1_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q1_weight"));
						question.setQuestion(form.get("q1_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q2_weight")) && StringUtils.isNotEmpty(form.get("q2_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q2_weight"));
						question.setQuestion(form.get("q2_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q3_weight")) && StringUtils.isNotEmpty(form.get("q3_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q3_weight"));
						question.setQuestion(form.get("q3_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q4_weight")) && StringUtils.isNotEmpty(form.get("q4_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q4_weight"));
						question.setQuestion(form.get("q4_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q5_weight")) && StringUtils.isNotEmpty(form.get("q5_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q5_weight"));
						question.setQuestion(form.get("q5_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q6_weight")) && StringUtils.isNotEmpty(form.get("q6_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q6_weight"));
						question.setQuestion(form.get("q6_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q7_weight")) && StringUtils.isNotEmpty(form.get("q7_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q7_weight"));
						question.setQuestion(form.get("q7_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q8_weight")) && StringUtils.isNotEmpty(form.get("q8_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q8_weight"));
						question.setQuestion(form.get("q8_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q9_weight")) && StringUtils.isNotEmpty(form.get("q9_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q9_weight"));
						question.setQuestion(form.get("q9_text"));

						guide.addQuestion(question);
					}
					if (StringUtils.isNotEmpty(form.get("q10_weight")) && StringUtils.isNotEmpty(form.get("q10_text"))) {
						InterviewGuideQuestionBean question = new InterviewGuideQuestionBean();

						question.setWeight(form.getDouble("q10_weight"));
						question.setQuestion(form.get("q10_text"));

						guide.addQuestion(question);
					}

					if (guide.getQuestionCount() > 0) {
						InterviewGuideManager.addInterviewGuideBean(guide);
						if(guide.getActiveList()){
							request.setAttribute("tguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("T"));
							request.setAttribute("sguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("S"));
							request.setAttribute("gstatus", "Active");
						}else{
							request.setAttribute("tguides", InterviewGuideManager.getInactiveInterviewGuideBeansByType("T"));
							request.setAttribute("sguides", InterviewGuideManager.getInactiveInterviewGuideBeansByType("S"));
							request.setAttribute("gstatus", "Inactive");
						}
						//request.setAttribute("guides", InterviewGuideManager.getInterviewGuideBeans());
						request.setAttribute("msg", "Interview guided added.");
						path = "admin_list_interview_guides.jsp";
					}
					else {
						request.setAttribute("msg", "At least one questions must be entered.");
						path = "admin_add_interview_guide.jsp";
					}
				}
				else {
					request.setAttribute("msg", validator.getErrorString());
					path = "admin_add_interview_guide.jsp";
				}

			}
			else {
				path = "admin_add_interview_guide.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "admin_index.jsp";
		}

		return path;
	}

}
