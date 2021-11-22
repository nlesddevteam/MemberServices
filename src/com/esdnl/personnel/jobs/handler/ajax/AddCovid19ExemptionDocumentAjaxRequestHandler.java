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
import com.esdnl.personnel.jobs.constants.DocumentTypeSS;
import com.esdnl.personnel.jobs.dao.ApplicantCovid19LogManager;
import com.esdnl.personnel.jobs.dao.ApplicantDocumentManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddCovid19ExemptionDocumentAjaxRequestHandler extends RequestHandlerImpl{
	public AddCovid19ExemptionDocumentAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-COVID19"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("appid"),new RequiredFormElement("doctype")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			StringBuilder sbfiles=new StringBuilder();
			String message="";
			if (validate_form()) {
				try {
					
					String filename = save_file("edoc",
							ApplicantDocumentBean.DOCUMENT_BASEPATH + form.get("doctype"));

					ApplicantDocumentBean doc = new ApplicantDocumentBean();
					ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("appid"));
					doc.setApplicant(profile);
					doc.setFilename(filename);
					if(form.getInt("doctype") == DocumentType.COVID19_VAX.getValue()) {
						doc.setType(DocumentType.get(form.getInt("doctype")));
						ApplicantDocumentManager.addApplicantDocumentBean(doc);
					}else {
						doc.setTypeSS(DocumentTypeSS.get(form.getInt("doctype")));
						ApplicantDocumentManager.addApplicantDocumentBeanSS(doc,form.getInt("doctype"));
					}
					
					
					ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();
					audit.setActionType(ActionTypeConstant.CREATE);
					if(form.getInt("doctype") == DocumentType.COVID19_VAX.getValue()) {
						audit.setAction("Applicant Document Uploaded - " + doc.getType().getDescription());
					}else {
						audit.setAction("Applicant Document Uploaded - " + doc.getTypeSS().getDescription());
					}
					
					audit.setApplication(ApplicationConstant.PERSONNEL);
					audit.setObjectType(ApplicantDocumentBean.class.toString());
					audit.setObjectId(Integer.toString(doc.getDocumentId()));
					audit.setWhen(Calendar.getInstance().getTime());
					audit.setWho(profile.getUID());
					audit.saveBean();
					
					// now add the covid log record
					ApplicantCovid19LogManager.addCovid19Excemption(doc.getDocumentId(),usr.getLotusUserFullNameReverse());
					//now we get the doc list and sent back
					ArrayList<ApplicantDocumentBean> list = new ArrayList<>();
					if(profile.getProfileType().contentEquals("S")) {
						list = new ArrayList<ApplicantDocumentBean>(ApplicantDocumentManager.getApplicantDocumentSSBean(profile, DocumentTypeSS.get(form.getInt("doctype"))));
					}else {
						list = new ArrayList<ApplicantDocumentBean>(ApplicantDocumentManager.getApplicantDocumentBean(profile, DocumentType.get(form.getInt("doctype"))));
					}
					
					for(ApplicantDocumentBean ad : list) {
						//now we create the xml for each document
						sbfiles.append("<CFILE>");
						if(ad.getClBean().isExcemptionDoc()) {
							sbfiles.append("<FILETITLE>" + "COVID 19 Exemption" + "</FILETITLE>");
							sbfiles.append("<FSTATUS>E</FSTATUS>");
							sbfiles.append("<VDATE>" + ad.getClBean().getDateVerifiedFormatted() + "</VDATE>");
							sbfiles.append("<VBY>" + ad.getClBean().getVerifiedBy() + "</VBY>");
							sbfiles.append("<RDATE></RDATE>");
							sbfiles.append("<RBY></RBY>");
							
						}else {
							sbfiles.append("<FILETITLE>" + "COVID 19 Vaccination" + "</FILETITLE>");
							if(ad.getClBean().getDateVerified() == null) {
								//so verified date and by null
								sbfiles.append("<VDATE></VDATE>");
								sbfiles.append("<VBY></VBY>");
								if(ad.getClBean().getRejectedDate() ==  null) {
									//not verified or rejected
									sbfiles.append("<RDATE></RDATE>");
									sbfiles.append("<RBY></RBY>");
									sbfiles.append("<FSTATUS>NV</FSTATUS>");
								}else {
									sbfiles.append("<RDATE>" + ad.getClBean().getRejectedDateFormatted() + "</RDATE>");
									sbfiles.append("<RBY>" + ad.getClBean().getRejectedBy() + "</RBY>");
									sbfiles.append("<RNOTES>" + ad.getClBean().getRejectedNotes() + "</RNOTES>");
									sbfiles.append("<FSTATUS>R</FSTATUS>");
								}
							}else {
								sbfiles.append("<VDATE>" + ad.getClBean().getDateVerifiedFormatted() + "</VDATE>");
								sbfiles.append("<VBY>" + ad.getClBean().getVerifiedBy() + "</VBY>");
								sbfiles.append("<RDATE></RDATE>");
								sbfiles.append("<RBY></RBY>");
								sbfiles.append("<FSTATUS>V</FSTATUS>");
							}
						}
						sbfiles.append("<DOCID>" + ad.getDocumentId() + "</DOCID>");
						sbfiles.append("<DOCUPLOAD>" + ad.getCreatedDateFormatted() + "</DOCUPLOAD>");
						sbfiles.append("<MESSAGE>ADDED</MESSAGE>");
						sbfiles.append("</CFILE>");
					}
					
					
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<DOCUMENTS>");
			sb.append("<DOCUMENT>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<C19FILES>");
			sb.append(sbfiles.toString());
			sb.append("</C19FILES>");
			sb.append("</DOCUMENT>");
			sb.append("</DOCUMENTS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}
