package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;
import com.esdnl.tsdoc.manager.TrusteeNoteAuditManager;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class ViewNoteAuditRequestHandler extends RequestHandlerImpl {

	public ViewNoteAuditRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
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
				request.setAttribute("audits", TrusteeNoteAuditManager.getTrusteeNoteAuditBeans(note));

				path = "view_note_audit.jsp";
			}
			else {
				request.setAttribute("msg", validator.getErrorString());

				path = "index.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);

			path = "index.jsp";
		}

		return path;
	}

}
