package com.esdnl.tsdoc.site.handler.trustee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TrusteeNoteAuditBean;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;
import com.esdnl.tsdoc.manager.TrusteeNoteAuditManager;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class ViewNoteRequestHandler extends RequestHandlerImpl {

	public ViewNoteRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-TRUSTEE-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("note-id")
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			if (validate_form()) {
				TrusteeNoteBean note = TrusteeNoteManager.getTrusteeNoteBean(form.getInt("note-id"));

				request.setAttribute("note", note);

				TrusteeNoteAuditBean audit = new TrusteeNoteAuditBean();
				audit.setAction(AuditActionBean.READ);
				audit.setAuditDate(Calendar.getInstance().getTime());
				audit.setNote(note);
				audit.setPersonnel(usr.getPersonnel());
				TrusteeNoteAuditManager.addTrusteeNoteAuditBean(audit);

				path = "view_note.jsp";
			}
			else {
				PrintWriter out = reponse.getWriter();
				out.println(validator.getErrorString());
				out.flush();

				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
