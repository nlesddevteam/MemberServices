package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;

public class DeleteDocumentRequestHandler extends RequestHandlerImpl {

	public DeleteDocumentRequestHandler() {

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

				try {

					TrusteeDocumentBean doc = TrusteeDocumentManager.getTrusteeDocumentBean(form.getInt("doc-id"));

					TrusteeDocumentManager.deleteTrusteeDocumentBean(form.getInt("doc-id"));

					delete_file("/WEB-INF/trustee-documents/", doc.getFilename());

					request.setAttribute("msg", "Document deleted successfully.");
					request.setAttribute("documents", TrusteeDocumentManager.getTrusteeDocumentBeans());

					path = "view_all_documents.jsp";
				}
				catch (TSDocException e) {
					request.setAttribute("msg", e.getMessage());

					path = "index.jsp";
				}
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
