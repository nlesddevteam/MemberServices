package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantNLESDPermanentExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantNLESDPermExpManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class AddApplicantNLESDPermExperienceRequestHandler   extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
	IOException {
		super.handleRequest(request, response);
		String path;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;
		try {
			String from = form.get("from_date");
			String to = form.get("to_date");
			String school_id = form.get("school_id");
			String grds_subs = form.get("grds_subs");
			profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
			if (profile == null) {
				path = "applicant_registration_step_1.jsp";
			}
			else if (StringUtils.isEmpty(from)) {
				request.setAttribute("errmsg", "Please specify from date.");
				path = "applicant_registration_step_2_perm_exp.jsp";
			}
			else if (StringUtils.isEmpty(to)) {
				request.setAttribute("errmsg", "Please specify to date.");
				path = "applicant_registration_step_2_perm_exp.jsp";
			}
			else if (StringUtils.isEmpty(school_id)) {
				request.setAttribute("errmsg", "Please specify school.");
				path = "applicant_registration_step_2_perm_exp.jsp";
			}
			else if (StringUtils.isEmpty(grds_subs)) {
				request.setAttribute("errmsg", "Please specify grades and/or subjects.");
				path = "applicant_registration_step_2_perm_exp.jsp";
			}
			else {
				sdf = new SimpleDateFormat("MM/yyyy");
				ApplicantNLESDPermanentExperienceBean abean = new ApplicantNLESDPermanentExperienceBean();
				abean.setSIN(profile.getSIN());
				abean.setFrom(sdf.parse(from));
				abean.setTo(sdf.parse(to));
				abean.setSchoolId(Integer.parseInt(school_id));
				abean.setGradesSubjects(grds_subs);
				ApplicantNLESDPermExpManager.addApplicantNLESDPermanentExperienceBean(abean);
				request.setAttribute("msg", "Permanent Experience successfully added.");
				path = "applicant_registration_step_2_perm_exp.jsp";
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant profile Permanent experience.");			
			path = "applicant_registration_step_2_perm_exp.jsp";
		}
		catch (ParseException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Invalid date format.");
			path = "applicant_registration_step_2_perm_exp.jsp";
		}
		return path;
	}
}
