package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.SchoolRegistrantCapBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListSchoolRegistrantCapsRequestHandler extends RequestHandlerImpl {

	public ListSchoolRegistrantCapsRequestHandler() {

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

				Collection<SchoolRegistrantCapBean> caps = KindergartenRegistrationManager.getSchoolRegistrantCapBeans(form.get("ddl_SchoolYear"));
				request.setAttribute("caps", caps);

				int total = 0, english = 0, englishw = 0, french = 0, frenchw = 0;

				for (SchoolRegistrantCapBean c : caps) {
					total += c.getSummary().getTotalRegistrants();
					english += c.getSummary().getEnglishRegistrants();
					englishw += (((c.getSummary().getEnglishRegistrants() - c.getEnglishCap()) < 0) ? 0
							: (c.getSummary().getEnglishRegistrants() - c.getEnglishCap()));
					french += c.getSummary().getFrenchRegistrants();
					frenchw += (((c.getSummary().getFrenchRegistrants() - c.getFrenchCap()) < 0) ? 0
							: (c.getSummary().getFrenchRegistrants() - c.getFrenchCap()));
				}
				request.setAttribute("totalreg", total);
				request.setAttribute("totaleng", english);
				request.setAttribute("englishw", englishw);
				request.setAttribute("totalfr", french);
				request.setAttribute("frenchw", frenchw);
			}
			catch (SchoolRegistrationException e) {
				request.setAttribute("msg", e.getMessage());
			}
		}
		else {

		}

		this.path = "district_school_registrant_caps.jsp";

		return this.path;
	}
}
