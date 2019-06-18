package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeDocumentAuditBean;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.manager.TrusteeDocumentAuditManager;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;
import com.esdnl.tsdoc.manager.TrusteeGroupDocumentManager;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;

public class AddDocumentRequestHandler extends RequestHandlerImpl {

	public AddDocumentRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("document-title"), new RequiredFileFormElement("document-file", new String[] {
					".pdf"
				})
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			if (form.hasValue("op", "confirm")) {
				if (validate_form()) {

					try {
						TrusteeDocumentBean doc = new TrusteeDocumentBean();

						doc.setDocumentTitle(form.get("document-title"));
						if (form.uploadFileExists("document-file"))
							doc.setFilename(save_file("document-file", "/WEB-INF/trustee-documents/"));

						doc = TrusteeDocumentManager.addTrusteeDocumentBean(doc);

						if (doc.getDocumentId() > 0) {
							TrusteeDocumentAuditBean audit = new TrusteeDocumentAuditBean();
							audit.setAuditDate(Calendar.getInstance().getTime());
							audit.setDocumentId(doc.getDocumentId());
							audit.setPersonnel(usr.getPersonnel());
							audit.setAction(AuditActionBean.ADD);
							TrusteeDocumentAuditManager.addTrusteeDocumentAuditBean(audit);

							if (form.exists("committee-id")) {
								TrusteeGroupBean group = null;
								for (int committeeId : form.getIntArray("committee-id")) {
									group = TrusteeGroupManager.getTrusteeGroupBean(committeeId);

									if (group != null) {
										TrusteeGroupDocumentManager.addTrusteeGroupDocumentBean(group, doc);

										audit = new TrusteeDocumentAuditBean();
										audit.setAuditDate(Calendar.getInstance().getTime());
										audit.setDocumentId(doc.getDocumentId());
										audit.setPersonnel(usr.getPersonnel());
										audit.setAction(AuditActionBean.ACCESSGRANTED);
										audit.setActionComment("Access granted to \"" + group.getGroupName() + "\"");
										TrusteeDocumentAuditManager.addTrusteeDocumentAuditBean(audit);
									}
								}
							}

							for (Personnel p : TrusteeDocumentManager.getDocumentPersonnelBeans(doc)) {
								if (p.equals(usr.getPersonnel()))
									continue;

								EmailBean email = new EmailBean();
								email.setFrom("ms@nlesd.ca");
								email.setSubject("NLESD Notification: Document available for your review.");
								email.setBody("Please be advised that there is a new document available for you review located in the Trustee Secure Document Repository. "
										+ "Click link below to login. Thank you.<BR><BR><a href='https://www.nlesd.ca/MemberServices'><b>Click Here To Login</b></a><BR><BR>"
										+ "Member Services");
								email.setTo(p.getEmailAddress());
								email.send();

								audit = new TrusteeDocumentAuditBean();
								audit.setAuditDate(Calendar.getInstance().getTime());
								audit.setDocumentId(doc.getDocumentId());
								audit.setPersonnel(p);
								audit.setAction(AuditActionBean.EMAILNOTIFICATION);
								audit.setActionComment(null);
								TrusteeDocumentAuditManager.addTrusteeDocumentAuditBean(audit);
							}

							request.setAttribute("msg", "Document added successfully.");
						}
						else
							request.setAttribute("msg", "Document could not be added.");

						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "add_document.jsp";
					}
					catch (TSDocException e) {
						request.setAttribute("msg", e.getMessage());
						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "add_document.jsp";
					}
				}
				else {
					request.setAttribute("msg", validator.getErrorString());
					request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

					path = "add_document.jsp";
				}
			}
			else {
				request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

				path = "add_document.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
