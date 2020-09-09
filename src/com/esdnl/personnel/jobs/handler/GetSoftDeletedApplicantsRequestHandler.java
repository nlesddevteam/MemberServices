package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetSoftDeletedApplicantsRequestHandler extends RequestHandlerImpl {
	public GetSoftDeletedApplicantsRequestHandler() {
		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
				ApplicantProfileBean[] applicants = ApplicantProfileManager.getSoftDeletedApplicants();
				request.setAttribute("applicants",applicants);
				path = "admin_view_deleted_applicants.jsp";
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve applicant records.");
			path = "admin_index.jsp";
		}

		return path;
	}
}