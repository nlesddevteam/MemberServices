package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteInterviewGuideRequestHandler extends RequestHandlerImpl {

	public DeleteInterviewGuideRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("guideId", "Guide ID is required.")
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

		if (validate_form()) {
			try {
				InterviewGuideManager.deleteInterviewGuideBean(form.getInt("guideId"));
				request.setAttribute("tguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("T"));
				request.setAttribute("sguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("S"));
				request.setAttribute("gstatus", "Active");
				path = "admin_list_interview_guides.jsp";
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());

				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());

			path = "admin_index.jsp";
		}

		return path;
	}

}
