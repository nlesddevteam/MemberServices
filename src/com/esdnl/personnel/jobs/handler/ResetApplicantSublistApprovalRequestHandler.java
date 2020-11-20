package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubListAuditBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.SublistAuditTypeCostant;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantSubListAuditManager;
import com.esdnl.personnel.jobs.dao.ApplicantSubListInfoManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ResetApplicantSublistApprovalRequestHandler extends RequestHandlerImpl {

	public ResetApplicantSublistApprovalRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-APROVETO-SUBLIST"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("uid"), new RequiredFormElement("list_id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		path = "admin_view_applicant.jsp";

		if (validate_form()) {
			try {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("uid"));

				if (profile != null) {
					ApplicantSubListInfoManager.resetApplicantSublistApproval(form.get("uid"), form.getInt("list_id"));
					//add audit trail entry for sub list activation
					ApplicantSubListAuditBean audbean = new ApplicantSubListAuditBean();
					audbean.setApplicantId(profile.getSIN());//no applicant sub list entry
					audbean.setSubListId(form.getInt("list_id"));
					audbean.setEntryType(SublistAuditTypeCostant.APPLICANTRESET);
					audbean.setEntryBy(usr.getPersonnel());
					String slnotes = " Notes:" + (form.get("slnotes") != null?form.get("slnotes"):"");
					audbean.setEntryNotes("Applicant Reset By: " + usr.getLotusUserFullName() + slnotes);
					ApplicantSubListAuditManager.addApplicantSubListAuditBean(audbean);
					request.setAttribute("APPLICANT", profile);
				}
				else {
					path = "admin_index.jsp";

					request.setAttribute("msg", "Could not find applicant profile.");
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);

				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

				path = "admin_index.jsp";
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

			path = "admin_index.jsp";
		}

		return path;
	}
}
