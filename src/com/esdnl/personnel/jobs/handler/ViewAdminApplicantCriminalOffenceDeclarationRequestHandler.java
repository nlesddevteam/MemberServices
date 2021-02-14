package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.personnel.jobs.bean.ApplicantCriminalOffenceDeclarationBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantCriminalOffenceDeclarationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewAdminApplicantCriminalOffenceDeclarationRequestHandler extends RequestHandlerImpl {

	public ViewAdminApplicantCriminalOffenceDeclarationRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL","PERSONNEL-OTHER-MANAGER-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				ApplicantCriminalOffenceDeclarationBean cod = ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBean(
						form.getInt("id"));

				if (cod == null || usr == null) {
					throw new SecurityException("Illegal access attempt to view declaration.");
				}

				ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

				audit.setActionType(ActionTypeConstant.VIEW);
				audit.setAction("Applicant Criminal Offence Declaration Viewed");
				audit.setApplication(ApplicationConstant.PERSONNEL);
				audit.setObjectType(ApplicantCriminalOffenceDeclarationBean.class.toString());
				audit.setObjectId(Integer.toString(cod.getDeclarationId()));
				audit.setWhen(Calendar.getInstance().getTime());
				audit.setWho(Integer.toString(usr.getPersonnel().getPersonnelID()));

				audit.saveBean();

				request.setAttribute("cod", cod);

				path = "admin_view_applicant_cod.jsp";
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);
			}
		}
		else
			request.setAttribute("msg", this.validator.getErrorString());

		return path;
	}
}
