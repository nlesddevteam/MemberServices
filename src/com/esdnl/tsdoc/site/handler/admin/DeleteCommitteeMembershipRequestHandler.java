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

public class DeleteCommitteeMembershipRequestHandler extends RequestHandlerImpl {

	public DeleteCommitteeMembershipRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("committee-id"), new RequiredFormElement("member-id")
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
					TrusteeGroupBean committee = TrusteeGroupManager.getTrusteeGroupBean(form.getInt("committee-id"));

					if (committee != null) {
						Personnel member = PersonnelDB.getPersonnel(form.getInt("member-id"));

						if (member != null) {
							TrusteeGroupPersonnelManager.deleteTrusteeGroupPersonnelBean(committee, member);
							request.setAttribute("msg", "\"" + member.getFullNameReverse() + "\" removed from committee.");
						}
						else
							request.setAttribute("msg", "Member not found and could not be removed from committee.");

						request.setAttribute("committee", committee);
						path = "committee_membership.jsp";
					}
					else {
						request.setAttribute("msg", "Committee could not be found.");
						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "committees.jsp";
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
