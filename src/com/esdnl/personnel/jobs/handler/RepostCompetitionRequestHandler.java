package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class RepostCompetitionRequestHandler extends RequestHandlerImpl {

	public RepostCompetitionRequestHandler() {

		requiredRoles = new String[] {
				"ADMINISTRATOR", "SEO - PERSONNEL"
		};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("comp_num")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(form.get("comp_num"));

				if (job != null) {
					if (!job.isClosed()) {
						request.setAttribute("msg", "Only a closed job can be reposted.");
						path = "view_job_post.jsp?comp_num=" + job.getCompetitionNumber();
					}
					else if (job.isAwarded()) {
						request.setAttribute("msg", "An awarded job cannot be reposted.");
						path = "view_job_post.jsp?comp_num=" + job.getCompetitionNumber();
					}
					else if (job.isCancelled()) {
						request.setAttribute("msg", "A cancelled job cannot be reposted.");
						path = "view_job_post.jsp?comp_num=" + job.getCompetitionNumber();
					}
					else {
						Calendar cal = Calendar.getInstance();

						job.setListingDate(cal.getTime());

						cal.add(Calendar.DAY_OF_MONTH, 7);
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
							cal.add(Calendar.DAY_OF_MONTH, 2);
						}
						else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
							cal.add(Calendar.DAY_OF_MONTH, 1);
						}

						job.setCompetitionEndDate(cal.getTime());
						job.setShortlistCompleteDate(null);

						JobOpportunityManager.updateJobOpportunityBean(job);

						path = "view_job_post.jsp?comp_num=" + job.getCompetitionNumber();
					}
				}
				else {
					request.setAttribute("msg", "Could not find competition " + form.get("comp_num"));
					path = "admin_index.jsp";
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not repost competition");
				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
		}

		return path;
	}
}
