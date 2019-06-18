package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeDocumentAuditBean;
import com.esdnl.tsdoc.bean.TrusteeNoteAuditBean;
import com.esdnl.tsdoc.manager.TrusteeDocumentAuditManager;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;
import com.esdnl.tsdoc.manager.TrusteeNoteAuditManager;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class MarkAsReadRequestHandler extends RequestHandlerImpl {

	public MarkAsReadRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			int noteids[] = form.getIntArray("note-id");

			if (noteids != null) {
				for (int id : noteids) {
					try {
						TrusteeNoteAuditBean audit = new TrusteeNoteAuditBean();
						audit.setAction(AuditActionBean.READ);
						audit.setAuditDate(Calendar.getInstance().getTime());
						audit.setNote(TrusteeNoteManager.getTrusteeNoteBean(id));
						audit.setPersonnel(usr.getPersonnel());
						TrusteeNoteAuditManager.addTrusteeNoteAuditBean(audit);
					}
					catch (TSDocException e) {
						e.printStackTrace();
					}
				}
			}

			int docids[] = form.getIntArray("document-id");

			if (docids != null) {
				for (int id : docids) {
					try {
						TrusteeDocumentAuditBean audit = new TrusteeDocumentAuditBean();
						audit.setAction(AuditActionBean.READ);
						audit.setAuditDate(Calendar.getInstance().getTime());
						audit.setDocument(TrusteeDocumentManager.getTrusteeDocumentBean(id));
						audit.setPersonnel(usr.getPersonnel());
						TrusteeDocumentAuditManager.addTrusteeDocumentAuditBean(audit);
					}
					catch (TSDocException e) {
						e.printStackTrace();
					}
				}
			}

			request.setAttribute("notes", TrusteeNoteManager.getUnreadTrusteeNoteBeans(usr.getPersonnel()));
			request.setAttribute("docs", TrusteeDocumentManager.getUnreadTrusteeDocumentBeans(usr.getPersonnel()));

			path = "index.jsp";

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
