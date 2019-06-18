package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.OtherJobOpportunityManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewJobPostsOtherRequestHandler extends RequestHandlerImpl {

	public ViewJobPostsOtherRequestHandler() {
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
				OtherJobOpportunityBean[] job = OtherJobOpportunityManager.getOtherJobOpportunityBeans();
	
					request.setAttribute("guide", job);
				
					

					path = "admin_view_job_posts_other.jsp";

			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());

				path = "admin_index.jsp";
			}
		
		return path;
	}
}

