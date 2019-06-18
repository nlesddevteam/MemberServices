package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherSSBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherSSManager;
import com.esdnl.util.StringUtils;
public class AddApplicantEducationOtherSSRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		ApplicantProfileBean profile = null;

		try {
			String other_info = request.getParameter("other_info");

			profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");

			if (profile == null) {
				path = "applicant_registration_step_1_ss.jsp";
			}
			else {
				if (!StringUtils.isEmpty(other_info)) {
					ApplicantEducationOtherSSBean ibean = new ApplicantEducationOtherSSBean();
						ibean.setSIN(profile.getSIN());
						ibean.setOtherInformation(other_info);
						ApplicantEducationOtherSSManager.addApplicantEducationOtherSSBean(ibean);
						request.setAttribute("msg", "Other information successuflly added.");
						path = "applicant_registration_step_6_ss.jsp";
				}
				else {
					ApplicantEducationOtherSSManager.deleteApplicantEducationOtherSSBean(profile);
					request.setAttribute("msg", "Successfully removed Other Info.");
					path = "applicant_registration_step_6_ss.jsp";
				}
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add other information.");
			path = "applicant_registration_step_6_ss.jsp";
		}

		return path;
	}
}