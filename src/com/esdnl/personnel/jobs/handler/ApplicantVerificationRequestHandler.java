package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantVerificationBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantVerificationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ApplicantVerificationRequestHandler extends RequestHandlerImpl {
	public ApplicantVerificationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("appid", "Applicant Id required for verification")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			try
			{
				String appid = form.get("appid");
				ApplicantVerificationBean abean = new ApplicantVerificationBean();
				abean.setApplicantId(appid);
				abean.setVerifiedBy(usr.getPersonnel().getPersonnelID());
				ApplicantVerificationManager.addApplicantVerificationBean(abean);
				request.setAttribute("msg", "Applicant profile has been verified.");
				ApplicantProfileBean profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
				ApplicantProfileBean refreshprofile = ApplicantProfileManager.getApplicantProfileBean(profile.getSIN());
				if(profile.getProfileType().equals("S")) {
					path = "admin_view_applicant_ss.jsp";
				}else {
					path = "admin_view_applicant.jsp";
				}
				request.setAttribute("APPLICANT",refreshprofile );
			}catch(Exception ex) {
				System.out.println(ex.getStackTrace());
				path = "admin_index.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}
		return path;
	}
}