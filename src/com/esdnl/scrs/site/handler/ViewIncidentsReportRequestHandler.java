package com.esdnl.scrs.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.IncidentBean;
import com.esdnl.scrs.service.IncidentService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewIncidentsReportRequestHandler extends RequestHandlerImpl {

	public ViewIncidentsReportRequestHandler() {

		this.requiredPermissions = new String[] {
				"BULLYING-ANALYSIS-SCHOOL-VIEW", "BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});

		try {
			if (validate_form()) {

				IncidentBean incident = IncidentService.getIncidentBean(form.getInt("id"), false);

				// check that non-admin can only view incidents of their school
				if (usr.checkPermission("BULLYING-ANALYSIS-SCHOOL-VIEW")) {
					if (!incident.getSchool().equals(usr.getPersonnel().getSchool()))
						throw new SecurityException("You only have permission to access reports for "
								+ usr.getPersonnel().getSchool().getSchoolName() + ".");
				}

				IncidentService.loadIncidentBeanAssociatedData(incident);

				request.setAttribute("incident", incident);
			}
			else {
				System.out.println(validator.getErrorString());
			}

			path = "view_incident.jsp";
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			request.setAttribute("msg", e.getMessage());

			path = "index.html";
		}

		return path;
	}

}
