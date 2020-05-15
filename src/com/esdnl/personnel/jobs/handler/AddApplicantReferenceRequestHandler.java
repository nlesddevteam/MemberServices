package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantSupervisorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantSupervisorManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
public class AddApplicantReferenceRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantReferenceRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("full_name", "Please specify referencee name"),
				new RequiredFormElement("title", "Please specify title"),
				new RequiredFormElement("address", "Please specify address"),
				new RequiredFormElement("email", "Please specify email")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String path;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
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
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_8.jsp";
		}
		return path;
	}
}