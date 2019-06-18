package com.esdnl.tsdoc.site.handler.trustee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class ViewAllNotesRequestHandler extends RequestHandlerImpl {

	public ViewAllNotesRequestHandler() {

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
					TrusteeNoteManager.categorizeNotesBySchoolYear(TrusteeNoteManager.getGroupPersonnelNoteBeans(usr.getPersonnel())));

			path = "view_all_notes.jsp";
		}
		catch (TSDocException e) {
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}

}
