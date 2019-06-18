package com.esdnl.scrs.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.service.IncidentService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListSchoolIncidentsReportRequestHandler extends RequestHandlerImpl {

	public ListSchoolIncidentsReportRequestHandler() {

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
			new RequiredFormElement("schoolId")
		});

		try {
			if (validate_form()) {
				School school = SchoolDB.getSchool(form.getInt("schoolId"));

				if (usr.checkPermission("BULLYING-ANALYSIS-SCHOOL-VIEW")) {
					if (!school.equals(usr.getPersonnel().getSchool()))
						throw new SecurityException("You only have permission to access reports for "
								+ usr.getPersonnel().getSchool().getSchoolName() + ".");
				}

				request.setAttribute("school", school);
				request.setAttribute("incidents", IncidentService.getIncidentBeans(school, false));
			}
			else {
				System.out.println(validator.getErrorString());
			}

			path = "list_incidents.jsp";
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
