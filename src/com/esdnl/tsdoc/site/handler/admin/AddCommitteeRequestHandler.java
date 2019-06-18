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
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;

public class AddCommitteeRequestHandler extends RequestHandlerImpl {

	public AddCommitteeRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("committee_name")
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			if (form.hasValue("op", "confirm")) {
				if (validate_form()) {

					try {
						TrusteeGroupBean committee = new TrusteeGroupBean();
						committee.setGroupName(form.get("committee_name"));
						TrusteeGroupManager.addTrusteeGroupBean(committee);

						request.setAttribute("msg", "Committee added successfully.");
						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "add_committee.jsp";
					}
					catch (TSDocException e) {
						request.setAttribute("msg", e.getMessage());

						path = "add_committee.jsp";
					}
				}
				else {
					request.setAttribute("msg", validator.getErrorString());
					path = "add_committee.jsp";
				}
			}
			else
				path = "add_committee.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
