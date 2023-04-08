package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.ApplicantJobAppliedManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.msauditlog.bean.MsAuditLogBean;

public class SwitchProfileTypeRequestHandler extends RequestHandlerImpl {

	public SwitchProfileTypeRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-SWITCH-PROFILE"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("appid", "Applicant Id required for update"),
				new RequiredFormElement("ptype", "Profile Type required for update")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				//switch profile
				ApplicantProfileManager.switchProfileType(form.get("appid"), form.get("ptype"));
				ApplicantProfileBean abean = ApplicantProfileManager.getApplicantProfileBean(form.get("appid"));

				if (abean != null) {
					if (abean.getProfileType().equals("S")) {
						path = "admin_view_applicant_ss.jsp";
					}
					else {
						request.setAttribute("SUBLISTBEANS_BY_REGION", SubListManager.getSubListBeansByRegion(
									com.awsd.common.Utils.getCurrentSchoolYear(), SubstituteListConstant.TEACHER));
						path = "admin_view_applicant.jsp";
					}
		
					request.setAttribute("APPLICANT", abean);
					request.setAttribute("jobs", ApplicantJobAppliedManager.getApplicantJobsApplied(form.get("sin")));
					
					MsAuditLogBean adbean = new MsAuditLogBean();
					adbean.setMalAppName("MyHRP");
					adbean.setMalAction("Switch Profile");
					adbean.setMalBy(usr.getPersonnel().getPersonnelID());
					adbean.setMalObjectKey(-1);
					StringBuilder sb = new StringBuilder();
					sb.append("Profile (");
					sb.append(abean.getSIN());
					sb.append(") changed from ");
					if(abean.getProfileType().equals("S")) {
						sb.append(" Teaching to Support by ");
					}else {
						sb.append(" Support to Teaching by ");
					}
					sb.append(usr.getLotusUserFullName());
					//add audit bean
					adbean.setMalNotes(sb.toString());
					add_audit_log(adbean);
				}
				} catch (JobOpportunityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("msg", "Could not retrieve applicant record.");

					path = "admin_index.jsp";
				}
		}else {
			request.setAttribute("msg", validator.getErrorString());

			path = "admin_index.jsp";
		}

			
			return path;
	}
}

