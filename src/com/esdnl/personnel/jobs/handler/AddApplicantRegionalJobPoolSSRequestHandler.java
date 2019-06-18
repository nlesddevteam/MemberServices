package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.ApplicantPoolJobSSConstant;
import com.esdnl.personnel.jobs.dao.ApplicantRegionalJobPoolSSManager;
import com.esdnl.personnel.jobs.dao.ApplicantRegionalPreferenceManager;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
public class AddApplicantRegionalJobPoolSSRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		ApplicantProfileBean profile = null;
		int[] regions = form.getIntArray("region_id");
		profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
		if(profile == null)
	    {
	        path = "applicant_registration_step_1_ss.jsp";
	    }else{
			try {
				if (regions != null && regions.length > 0) {
					ApplicantRegionalPreferenceManager.addApplicantRegionalPreferences(profile, regions);
	}
				else {
					ApplicantRegionalPreferenceManager.clearApplicantRegionalPreferences(profile);
				}
				//remove the job types
				ApplicantRegionalJobPoolSSManager.deleteApplicantRegionalJobPoolSSBean(profile.getSIN());
				//now get the job types
				if(form.exists("custodian")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.CUSTODIAN.getValue());
				}
				if(form.exists("carpenter")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.CARPENTER.getValue());
				}
				if(form.exists("cleaner")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.CLEANER.getValue());
				}
				if(form.exists("electrician")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.ELECTRICIAN.getValue());
				}
				if(form.exists("secretary")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.SCHOOL_SECRETARY.getValue());
				}
				if(form.exists("officeadm")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.OFFICE_ADMIN.getValue());
				}
				if(form.exists("studentassistant")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.STUDENT_ASSISTANT.getValue());
				}
				if(form.exists("othertrades")){
					ApplicantRegionalJobPoolSSManager.addApplicantRegionalJobPoolSSBean(profile.getSIN(),ApplicantPoolJobSSConstant.OTHER.getValue());
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);
				request.setAttribute("errmsg", e.getMessage());
			}
	    }
		

		request.setAttribute("msg", "Pool preferences updated successfully.");
		path = "applicant_registration_step_7_ss.jsp";

		return path;

	}

}