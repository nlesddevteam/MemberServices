package com.esdnl.tsdoc.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class ViewTSDocRequestHandler extends RequestHandlerImpl {

	public ViewTSDocRequestHandler() {

		requiredPermissions = new String[] {
				"TSDOC-ADMIN-VIEW", "TSDOC-TRUSTEE-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		if (usr.checkPermission("TSDOC-ADMIN-VIEW")) {
			try {
				request.setAttribute("notes", TrusteeNoteManager.getUnreadTrusteeNoteBeans(usr.getPersonnel()));
				request.setAttribute("docs", TrusteeDocumentManager.getUnreadTrusteeDocumentBeans(usr.getPersonnel()));
			}
			catch (TSDocException e) {
				request.setAttribute("msg", e.getMessage());
			}

			path = "admin/index.jsp";
		}
		else if (usr.checkPermission("TSDOC-TRUSTEE-VIEW")) {
			try {
				request.setAttribute("notes", TrusteeNoteManager.getUnreadGroupPersonnelNoteBeans(usr.getPersonnel()));
				request.setAttribute("docs", TrusteeDocumentManager.getUnreadGroupPersonnelDocumentBeans(usr.getPersonnel()));
			}
			catch (TSDocException e) {
				request.setAttribute("msg", e.getMessage());
			}

			path = "trustee/index.jsp";
		}
		return path;
	}

}
