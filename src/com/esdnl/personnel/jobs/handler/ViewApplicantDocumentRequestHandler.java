package com.esdnl.personnel.jobs.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.personnel.jobs.bean.ApplicantDocumentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantDocumentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewApplicantDocumentRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public ViewApplicantDocumentRequestHandler() {

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

				if (!doc.getApplicant().getUID().equalsIgnoreCase(profile.getUID())) {
					request.setAttribute("msg", "Illegal access attempt to view document, access auditing established.");

					ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

					audit.setActionType(ActionTypeConstant.ILLEGAL_ACCESS);
					audit.setAction("Illegal access attempt to view document - " + doc.getType().getDescription());
					audit.setApplication(ApplicationConstant.PERSONNEL);
					audit.setObjectType(ApplicantDocumentBean.class.toString());
					audit.setObjectId(Integer.toString(doc.getDocumentId()));
					audit.setWhen(Calendar.getInstance().getTime());
					audit.setWho(profile.getUID());

					audit.saveBean();

					return "applicant_registration_step_10.jsp";
				}

				response.setHeader("Pragma", "private");
				response.setHeader("Cache-Control", "private, must-revalidate");
				response.setContentType("application/pdf");
				//response.setHeader("content-disposition ", "attachment");

				try {
					IOUtils.copy(new FileInputStream(ROOT_DIR + "/" + doc.getFilePath()), response.getOutputStream());
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

				audit.setActionType(ActionTypeConstant.VIEW);
				if(doc.getType() == null){
					//support staff doc
					audit.setAction("Applicant Document Viewed - " + doc.getTypeSS().getDescription());
				}else{
					audit.setAction("Applicant Document Viewed - " + doc.getType().getDescription());
				}
				audit.setApplication(ApplicationConstant.PERSONNEL);
				audit.setObjectType(ApplicantDocumentBean.class.toString());
				audit.setObjectId(Integer.toString(doc.getDocumentId()));
				audit.setWhen(Calendar.getInstance().getTime());
				audit.setWho(profile.getUID());

				audit.saveBean();
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
