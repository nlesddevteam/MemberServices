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
import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.dao.ApplicantDocumentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;

public class AddApplicantDocumentRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public AddApplicantDocumentRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredSelectionFormElement("lst_DocumentType", -1, "Document type is required."),
				new RequiredFileFormElement("applicant_document", "pdf", "Document file missing or has invalid type.")				
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String filename = save_file("applicant_document",
						ApplicantDocumentBean.DOCUMENT_BASEPATH + form.get("lst_DocumentType"));

				ApplicantDocumentBean doc = new ApplicantDocumentBean();

				doc.setApplicant(profile);
				doc.setFilename(filename);
				doc.setType(DocumentType.get(form.getInt("lst_DocumentType")));
				ApplicantDocumentManager.addApplicantDocumentBean(doc);
				
				request.setAttribute("msg", "Document uploaded successfully.");

				ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

				audit.setActionType(ActionTypeConstant.CREATE);
				audit.setAction("Applicant Document Uploaded - " + doc.getType().getDescription());
				audit.setApplication(ApplicationConstant.PERSONNEL);
				audit.setObjectType(ApplicantDocumentBean.class.toString());
				audit.setObjectId(Integer.toString(doc.getDocumentId()));
				audit.setWhen(Calendar.getInstance().getTime());
				audit.setWho(profile.getUID());

				audit.saveBean();
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("errmsg", e.getMessage());
			}
		}
		else
			request.setAttribute("errmsg", this.validator.getErrorString());

		path = "applicant_registration_step_10.jsp";

		return path;
	}

}
