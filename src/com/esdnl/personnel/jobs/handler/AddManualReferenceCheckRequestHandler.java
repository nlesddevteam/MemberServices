package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
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
			AdRequestBean ad = AdRequestManager.getAdRequestBean(job.getCompetitionNumber());
			request.setAttribute("AD_REQUEST_BEAN", ad);
			request.setAttribute("JOB", job);
			JobOpportunityAssignmentBean[] ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);
			request.setAttribute("ASS", ass);
			request.setAttribute("PROFILE", profile);
			if(reftype.equals("ADMIN"))
			{
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_admin_reference.jsp";
			}else  if (reftype.equals("GUIDE")){
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_guide_reference.jsp";
			}else  if (reftype.equals("TEACHER")){
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_teacher_reference.jsp";
			}else  if (reftype.equals("MANAGE")){
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_manage_reference.jsp";
			}else  if (reftype.equals("SUPPORT")){
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_support_reference.jsp";
			}else{
				//path = "nlesd_external_reference_checklist_app.jsp";
				request.setAttribute("mancheck", "Y");
				path = "add_nlesd_external_reference.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		request.setAttribute("hidesearch",true);
		return path;
	}
}
