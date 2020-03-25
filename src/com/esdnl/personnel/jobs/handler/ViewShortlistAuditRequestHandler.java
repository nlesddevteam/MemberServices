package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.dao.ApplicantShortlistAuditManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewShortlistAuditRequestHandler extends RequestHandlerImpl {

	public ViewShortlistAuditRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("comp_num")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		try {
			if (validate_form()) {
				request.setAttribute("auditlist",ApplicantShortlistAuditManager.getAuditReport(form.get("comp_num")));
				path = "admin_view_shortlist_audit.jsp";
			}else {
				request.setAttribute("msg", "Error retrieving shortlist audit.");
				path = "admin_view_shortlist_audit.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve shortlist audit");
			path = "admin_index.jsp";
		}

		return path;
	}
}
