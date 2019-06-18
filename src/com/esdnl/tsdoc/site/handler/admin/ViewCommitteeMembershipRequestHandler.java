package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;

public class ViewCommitteeMembershipRequestHandler extends RequestHandlerImpl {

	public ViewCommitteeMembershipRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new RequiredFormElement[] {
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

				TrusteeGroupBean committee = TrusteeGroupManager.getTrusteeGroupBean(form.getInt("id"));

				if (committee != null) {
					request.setAttribute("committee", committee);

					path = "committee_membership.jsp";
				}
				else {
					request.setAttribute("msg", "Committee [id=" + form.getInt("id") + "] not found.");
					request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

					path = "committees.jsp";
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());
				request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

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
