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
import com.esdnl.tsdoc.manager.TrusteeGroupManager;

public class DeleteCommitteeRequestHandler extends RequestHandlerImpl {

	public DeleteCommitteeRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
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
					TrusteeGroupManager.deleteTrusteeGroupBean(form.getInt("id"));

					request.setAttribute("msg", "Committee deleted successfully.");
					request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

					path = "committees.jsp";
				}
				catch (TSDocException e) {
					request.setAttribute("msg", e.getMessage());

					path = "committees.jsp";
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());
				path = "committees.jsp";
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
