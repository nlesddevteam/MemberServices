package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.SchoolRegistrantsSummaryBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListSchoolRegistrantsSummariesRequestHandler extends RequestHandlerImpl {

	public ListSchoolRegistrantsSummariesRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("ddl_SchoolYear", "School year is required.")
		});

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				request.setAttribute("sy", form.get("ddl_SchoolYear"));

				Collection<SchoolRegistrantsSummaryBean> summaries = KindergartenRegistrationManager.getSchoolRegistrantsSummaryBeans(form.get("ddl_SchoolYear"));
				request.setAttribute("summaries", summaries);

				int total = 0, english = 0, french = 0;

				for (SchoolRegistrantsSummaryBean s : summaries) {
					total += s.getTotalRegistrants();
					english += s.getEnglishRegistrants();
					french += s.getFrenchRegistrants();
				}
				request.setAttribute("totalreg", total);
				request.setAttribute("totaleng", english);
				request.setAttribute("totalfr", french);
			}
			catch (SchoolRegistrationException e) {
				request.setAttribute("msg", e.getMessage());
			}
		}
		else {

		}

		this.path = "district_school_registrant_summaries.jsp";

		return this.path;
	}
}
