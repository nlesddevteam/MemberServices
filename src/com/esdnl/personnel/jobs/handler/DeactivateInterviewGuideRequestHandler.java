package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.personnel.jobs.bean.InterviewGuideBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeactivateInterviewGuideRequestHandler extends RequestHandlerImpl {
	public DeactivateInterviewGuideRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
			};
	}

	public String handleRequest(HttpServletRequest request,
															HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("guideId"),new RequiredFormElement("activate")
			});

			if (validate_form()) {
				InterviewGuideBean ig = InterviewGuideManager.getInterviewGuideBean(form.getInt("guideId"));
				ig.setActiveList(form.getBoolean("activate"));
				InterviewGuideManager.updateInterviewGuideBean(ig);
				request.setAttribute("guide",ig);

				path = "admin_view_interview_guide.jsp";
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg",
						StringUtils.encodeHTML(validator.getErrorString()));

				path = "admin_view_interview_guide.jsp";
			}
		}

		catch (JobOpportunityException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ex) {}

			request.setAttribute("msg", "Could not deactivate/activate Interview Guide");

			path = "admin_view_interview_guide.jsp";
		}

		return path;
	}
}
