package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;
import com.esdnl.tsdoc.manager.TrusteeGroupPersonnelManager;

public class AddCommitteeMembershipRequestHandler extends RequestHandlerImpl {

	public AddCommitteeMembershipRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {
			if (form.hasValue("op", "confirm")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("committee-id"), new RequiredFormElement("member-id")
				});

				if (validate_form()) {
					try {
						TrusteeGroupBean committee = TrusteeGroupManager.getTrusteeGroupBean(form.getInt("committee-id"));
						Personnel member = PersonnelDB.getPersonnel(form.getInt("member-id"));

						if (committee == null) {
							request.setAttribute("msg", "Committee could not be found.");
							request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

							path = "committees.jsp";
						}
						else if (member == null) {
							request.setAttribute("msg", "Member could not be found.");
							request.setAttribute("committee", committee);
							request.setAttribute("members", PersonnelDB.getDistrictPersonnel());

							path = "add_committee_membership.jsp";
						}
						else {
							TrusteeGroupPersonnelManager.addTrusteeGroupPersonnelBean(committee, member);

							request.setAttribute("msg", "Membership added successfully.");
							request.setAttribute("committee", committee);
							request.setAttribute("members", PersonnelDB.getDistrictPersonnel());

							path = "add_committee_membership.jsp";
						}
					}
					catch (TSDocException e) {
						request.setAttribute("msg", e.getMessage());
						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "committees.jsp";
					}
				}
				else {
					request.setAttribute("msg", validator.getErrorString());
					request.setAttribute("members", PersonnelDB.getDistrictPersonnel());

					path = "add_committee_membership.jsp";
				}
			}
			else {
				validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("committee-id")
				});

				if (validate_form()) {
					request.setAttribute("committee", TrusteeGroupManager.getTrusteeGroupBean(form.getInt("committee-id")));
					request.setAttribute("members", PersonnelDB.getDistrictPersonnel());

					path = "add_committee_membership.jsp";
				}
				else {
					request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

					path = "committees.jsp";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
