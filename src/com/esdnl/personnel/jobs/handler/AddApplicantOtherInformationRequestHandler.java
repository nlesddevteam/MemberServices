package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantOtherInformationBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantOtherInfoManager;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class AddApplicantOtherInformationRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantOtherInformationRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String path = null;
		ApplicantProfileBean profile = null;

		try {
			String other_info = request.getParameter("other_info").trim();

			profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");


			if (!StringUtils.isEmpty(other_info)) {
				if (other_info.length() > 4000) {
					request.setAttribute("errmsg", "A maxium of 4000 characters can be entered.");
					path = "applicant_registration_step_7.jsp";
				}
				else {
					ApplicantOtherInformationBean ibean = new ApplicantOtherInformationBean();
					ibean.setSIN(profile.getSIN());
					ibean.setOtherInformation(other_info);
					ApplicantOtherInfoManager.addApplicantOtherInformationBean(ibean);
					request.setAttribute("msg", "Successfully updated profile information.");
					path = "applicant_registration_step_7.jsp";
				}
			}
			else {
				ApplicantOtherInfoManager.deleteApplicantOtherInformationBean(profile);
				request.setAttribute("msg", "Successfully updated profile information.");
				path = "applicant_registration_step_7.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant profileother information.");
			path = "applicant_registration_step_7.jsp";
		}

		return path;
	}
}