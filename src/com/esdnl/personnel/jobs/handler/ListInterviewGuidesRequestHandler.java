package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.InterviewGuideManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListInterviewGuidesRequestHandler extends RequestHandlerImpl {

	public ListInterviewGuidesRequestHandler() {

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
			String status = request.getParameter("status").toString();
			
			if(status.equals("active"))
			{
				//request.setAttribute("guides", InterviewGuideManager.getActiveInterviewGuideBeans());
				request.setAttribute("tguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("T"));
				request.setAttribute("sguides", InterviewGuideManager.getActiveInterviewGuideBeansByType("S"));
				request.setAttribute("gstatus", "Active");
			}else{
				//request.setAttribute("guides", InterviewGuideManager.getInactiveInterviewGuideBeans());
				request.setAttribute("tguides", InterviewGuideManager.getInactiveInterviewGuideBeansByType("T"));
				request.setAttribute("sguides", InterviewGuideManager.getInactiveInterviewGuideBeansByType("S"));
				request.setAttribute("gstatus", "Inactive");
			}
			path = "admin_list_interview_guides.jsp";
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "admin_index.jsp";
		}

		return path;
	}

}
