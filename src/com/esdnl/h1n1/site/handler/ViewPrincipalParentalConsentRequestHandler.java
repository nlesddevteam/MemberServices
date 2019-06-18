package com.esdnl.h1n1.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.security.User;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.constant.Grade;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewPrincipalParentalConsentRequestHandler extends RequestHandlerImpl {

	private School school;

	public ViewPrincipalParentalConsentRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-PRINCIPAL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("school_id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "principal_parental_consent.jsp";

		if (validate_form()) {

			try {
				User usr = (User) session.getAttribute("usr");
				school = usr.getPersonnel().getSchool();

				request.setAttribute("SCHOOLBEAN", school);
				request.setAttribute("GRADES", Grade.getList());
				request.setAttribute("CONSENTDATA", DailyReportManager.getConsentData(school));
			}
			catch (H1N1Exception e) {
				e.printStackTrace();
			}
		}
		else
			path = "principalView.html";

		return path;
	}

	public void auditRequest() {

		/*
		 * ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();
		 * 
		 * audit.setAction("View Principal Monthly Report - " + new
		 * SimpleDateFormat("MMMM yyyy").format(month));
		 * audit.setActionType(ActionTypeConstant.VIEW);
		 * audit.setApplication(ApplicationConstant.getH1N1());
		 * audit.setObjectId(Integer.toString(school.getSchoolID()));
		 * audit.setObjectType(school.getClass().getName());
		 * audit.setWho(usr.getPersonnel().getFullName());
		 * 
		 * audit.saveBean();
		 */
	}
}
