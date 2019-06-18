package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;
import com.esdnl.tsdoc.manager.TrusteeDocumentAuditManager;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;

public class ViewDocumentAuditRequestHandler extends RequestHandlerImpl {

	public ViewDocumentAuditRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("doc-id")
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			if (validate_form()) {
				TrusteeDocumentBean doc = TrusteeDocumentManager.getTrusteeDocumentBean(form.getInt("doc-id"));

				request.setAttribute("document", doc);
				request.setAttribute("audits", TrusteeDocumentAuditManager.getTrusteeDocumentAuditBeans(doc));

				path = "view_document_audit.jsp";
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
