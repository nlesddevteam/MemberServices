package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class AddManualReferenceCheckRequestHandler extends RequestHandlerImpl {
	public AddManualReferenceCheckRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			String uid = form.get("uid");
			String reftype = form.get("reftype");
			JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(uid);
			//add new request
			ReferenceCheckRequestBean refreq = new ReferenceCheckRequestBean();
			refreq.setCandidateId(uid);
			refreq.setCompetitionNumber(job.getCompetitionNumber());
			refreq.setCheckRequester(usr.getPersonnel());
			refreq.setReferenceType(reftype);
			refreq = ReferenceCheckRequestManager.addReferenceCheckRequestBean(refreq);
			request.setAttribute("REFERENCE_CHECK_REQUEST_BEAN", refreq);
			AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
			request.setAttribute("AD_REQUEST_BEAN", ad);
			request.setAttribute("JOB", job);
			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
			request.setAttribute("JOB_ASSIGNMENTS", ass);
			request.setAttribute("PROFILE", profile);
			if(reftype.equals("ADMIN"))
			{
				path = "nlesd_admin_reference_checklist.jsp";
			}else  if (reftype.equals("GUIDE"))
			{
				path = "nlesd_guide_reference_checklist.jsp";
			}else{
				path = "nlesd_teacher_reference_checklist.jsp";	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		return path;
	}
}
