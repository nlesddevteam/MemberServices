package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;

public class AddExternalNLESDReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl{
	public AddExternalNLESDReferenceCheckAppRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);

		try {
			String referenceType="";
			referenceType=form.get("reftype").toString();
			String referencereqid = form.get("refreq").toString();
			//now we load the correct page
			if(referenceType.equals("admin")){
				path = "nlesd_admin_reference_checklist_app.jsp";	
			}else if(referenceType.equals("guide")){
				path = "nlesd_guide_reference_checklist_app.jsp";
			}else if(referenceType.equals("teacher")){
				path = "nlesd_teacher_reference_checklist_app.jsp";
			}else if(referenceType.equals("external")){
				path = "nlesd_external_reference_checklist_app.jsp";
			}else if(referenceType.equals("support")){
				path = "nlesd_support_reference_checklist_app.jsp";
			}else if(referenceType.equals("manage")){
				path = "nlesd_manage_reference_checklist_app.jsp";
			}
			ApplicantRefRequestBean abean= ApplicantRefRequestManager.getApplicantRefRequestBean(Integer.parseInt(referencereqid));
			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(abean.getApplicantId());
			request.setAttribute("PROFILE", profile);
			request.setAttribute("REFREQUEST", abean);
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		return path;
	}
}
