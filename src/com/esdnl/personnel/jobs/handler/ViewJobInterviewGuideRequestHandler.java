package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewJobInterviewGuideRequestHandler extends RequestHandlerImpl {

	public ViewJobInterviewGuideRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("comp_num", "Competition number is required.")
		});

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW","PERSONNEL-OTHER-MANAGER-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if (job != null) {
					request.setAttribute("job", job);
					request.setAttribute("guide", InterviewGuideManager.getInterviewGuideBean(job));
					if(job.getIsSupport().equals("N")){
						request.setAttribute("guides", InterviewGuideManager.getActiveInterviewGuideBeansByType("T"));
					}else{
						request.setAttribute("guides", InterviewGuideManager.getActiveInterviewGuideBeansByType("S"));
					}

					path = "admin_view_job_interview_guide.jsp";
				}
				else {
					request.setAttribute("msg", "No job found for competition " + form.get("comp_num"));

					path = "admin_index.jsp";
				}
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
