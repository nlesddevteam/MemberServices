package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.personnel.jobs.bean.ApplicantDocumentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantDocumentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteAdminApplicantDocumentRequestHandler extends RequestHandlerImpl {

	public DeleteAdminApplicantDocumentRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW-DOCS"
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
				ApplicantDocumentBean doc = ApplicantDocumentManager.getApplicantDocumentBean(form.getInt("id"));

				if (doc != null) {

					ApplicantDocumentManager.deleteApplicantDocumentBean(doc.getDocumentId());
					if(doc.getApplicant().getProfileType().equals("T")){
						delete_file(ApplicantDocumentBean.DOCUMENT_BASEPATH + doc.getType().getValue(), doc.getFilename());
					}else{
						delete_file(ApplicantDocumentBean.DOCUMENT_BASEPATH + doc.getTypeSS().getValue(), doc.getFilename());
					}
					ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

					audit.setActionType(ActionTypeConstant.VIEW);
					if(doc.getApplicant().getProfileType().equals("T")){
						audit.setAction("Applicant Document Deleted - " + doc.getType().getDescription());
					}else{
						audit.setAction("Applicant Document Deleted - " + doc.getTypeSS().getDescription());
					}
					
					audit.setApplication(ApplicationConstant.PERSONNEL);
					audit.setObjectType(ApplicantDocumentBean.class.toString());
					audit.setObjectId(Integer.toString(doc.getDocumentId()));
					audit.setWhen(Calendar.getInstance().getTime());
					audit.setWho(Integer.toString(usr.getPersonnel().getPersonnelID()));

					audit.saveBean();

					request.getRequestDispatcher("viewApplicantProfile.html?sin=" + doc.getApplicant().getUID()).forward(request,
							response);
				}
				else {
					request.setAttribute("msg", "Document found not be found.");
					path = "admin_index.jsp";
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace(System.err);
			}
		}
		else
			request.setAttribute("msg", this.validator.getErrorString());

		return null;
	}
}
