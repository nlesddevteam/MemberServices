package com.esdnl.tsdoc.site.handler.trustee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;

public class ViewAllDocumentsRequestHandler extends RequestHandlerImpl {

	public ViewAllDocumentsRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-TRUSTEE-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {
			request.setAttribute(
					"years",
					TrusteeDocumentManager.categorizeDocumentsBySchoolYear(TrusteeDocumentManager.getGroupPersonnelDocumentBeans(usr.getPersonnel())));

			path = "view_all_documents.jsp";
		}
		catch (TSDocException e) {
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}

}
