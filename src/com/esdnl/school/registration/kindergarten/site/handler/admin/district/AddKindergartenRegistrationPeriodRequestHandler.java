package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.nlesd.school.service.SchoolZoneService;

public class AddKindergartenRegistrationPeriodRequestHandler extends RequestHandlerImpl {

	public AddKindergartenRegistrationPeriodRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("ddl_SchoolYear", "Please select a school year."),
				new RequiredFormElement("txt_StartDate", "Please choose a start date and time."),
				new RequiredPatternFormElement("txt_StartDate", FormElementPattern.DATE_TIME_PATTERN, "Start date and time has invalid format."),
				new RequiredFormElement("txt_EndDate", "Please choose an end date and time."),
				new RequiredPatternFormElement("txt_EndDate", FormElementPattern.DATE_TIME_PATTERN, "End date and time has invalid format."),
				new RequiredFormElement("txt_ConfirmationDeadlineDate", "Please choose an address confirmation deadline date and time."),
				new RequiredPatternFormElement("txt_ConfirmationDeadlineDate", FormElementPattern.DATE_TIME_PATTERN, "Address confirmation deadline date and time has invalid format.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {
			if (validate_form()) {
				KindergartenRegistrationPeriodBean period = new KindergartenRegistrationPeriodBean();

				period.setSchoolYear(form.get("ddl_SchoolYear"));
				period.setStartDate(form.getDateTime("txt_StartDate"));
				period.setEndDate(form.getDateTime("txt_EndDate"));
				period.setAddressConfirmationDeadline(form.getDateTime("txt_ConfirmationDeadlineDate"));

				String[] zones = form.getArray("txt_AssociatedZones");
				if (zones.length > 0) {
					for (String id : form.getArray("txt_AssociatedZones")) {
						period.addZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(id)));
					}
				}
				else {
					//all zones
					period.setZones(SchoolZoneService.getSchoolZoneBeans());
				}

				KindergartenRegistrationManager.addKindergartenRegistrationPeriodBean(period);

				request.setAttribute("msg", "Registration period added successfully.");
			}
			else {
				request.setAttribute("msg", this.validator.getErrorString());
			}

			request.setAttribute("periods", KindergartenRegistrationManager.getKindergartenRegistrationPeriodBeans());
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msg", e.getMessage());
		}

		request.setAttribute("zones", SchoolZoneService.getSchoolZoneBeans());

		this.path = "district_index.jsp";

		return this.path;
	}
}
