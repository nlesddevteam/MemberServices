package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantSupervisorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
public class AddApplicantReferenceRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		ApplicantProfileBean profile = null;

		try {
			String name = request.getParameter("full_name");
			String title = request.getParameter("title");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");

			profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");

			if (profile == null) {
				path = "applicant_registration_step_1.jsp";
			}else {
				ApplicantSupervisorBean abean = new ApplicantSupervisorBean();

				abean.setSIN(profile.getSIN());
				abean.setName(name);
				abean.setTitle(title);
				abean.setAddress(address);
				abean.setTelephone(telephone);

				ApplicantSupervisorManager.addApplicantSupervisorBean(abean);
				
				//now we get the id
				
				int id = ApplicantRefRequestManager.getApplicantSupervisorId(profile.getSIN());
				ApplicantRefRequestBean ebean = new ApplicantRefRequestBean();
				ebean.setFkAppSup(id);
				ebean.setEmailAddress(email);
				ebean.setApplicantId(abean.getSin());
				ApplicantRefRequestManager.addApplicantRefRequestBean(ebean);
				

				request.setAttribute("msg", "Reference successfully added.");
				path = "applicant_registration_step_8.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant reference.");
			path = "applicant_registration_step_8.jsp";
		}

		return path;
	}
}