package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.personnel.jobs.bean.ApplicantDocumentBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.dao.ApplicantDocumentManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddApplicantLetterAjaxRequestHandler extends RequestHandlerImpl {

	public AddApplicantLetterAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("lettertitle", "Letter title required."),
				new RequiredFormElement("applicantid", "Applicant id missing.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String filename = save_file("letterdocument",
						ApplicantDocumentBean.DOCUMENT_BASEPATH + DocumentType.LETTER.getValue());
				ApplicantDocumentBean dbean = new ApplicantDocumentBean();
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("applicantid"));
				dbean.setApplicant(profile);
				dbean.setDescription(form.get("lettertitle"));
				dbean.setFilename(filename);
				dbean.setType(DocumentType.LETTER);
				ApplicantDocumentManager.addApplicantDocumentBean(dbean);
				// add audit record
				ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

				audit.setActionType(ActionTypeConstant.CREATE);
				audit.setAction("Applicant Letter Uploaded");
				audit.setApplication(ApplicationConstant.PERSONNEL);
				audit.setObjectType(ApplicantDocumentBean.class.toString());
				audit.setObjectId(dbean != null ? Integer.toString(dbean.getDocumentId()) : null);
				audit.setWhen(Calendar.getInstance().getTime());
				audit.setWho(String.valueOf(usr.getPersonnel().getPersonnelID()));

				audit.saveBean();

				ArrayList<ApplicantDocumentBean> alist = (ArrayList<ApplicantDocumentBean>) ApplicantDocumentManager.getApplicantDocumentBean(
						profile, DocumentType.LETTER);
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-APPLICANT-LETTER-RESPONSE>");
				for (ApplicantDocumentBean abean : alist) {
					sb.append("<LETTER>");
					sb.append("<LETTERID>" + abean.getDocumentId() + "</LETTERID>");
					sb.append("<LETTERTITLE>" + abean.getDescription() + "</LETTERTITLE>");
					sb.append("<LETTERDATE>" + abean.getCreatedDateFormatted() + "</LETTERDATE>");
					sb.append("<MESSAGE>ADDED</MESSAGE>");
					sb.append("</LETTER>");
				}
				sb.append("</ADD-APPLICANT-LETTER-RESPONSE>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-APPLICANT-LETTER-RESPONSE>");
				sb.append("<LETTER>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</LETTER>");
				sb.append("</ADD-APPLICANT-LETTER-RESPONSE>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ADD-APPLICANT-LETTER-RESPONSE>");
			sb.append("<LETTER>");
			sb.append("<MESSAGE>" + this.validator.getErrorString() + "</MESSAGE>");
			sb.append("</LETTER>");
			sb.append("</ADD-APPLICANT-LETTER-RESPONSE>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}
