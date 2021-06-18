package com.nlesd.webmaint.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.school.service.SchoolZoneService;
import com.nlesd.webmaint.bean.StaffDirectoryContactBean;
import com.nlesd.webmaint.service.StaffDirectoryService;

public class MSUpdateStaffDirectoryContactRequestHandler extends RequestHandlerImpl {

	public MSUpdateStaffDirectoryContactRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW","WEBMAINTENANCE-STAFFING"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("divisionId"), new RequiredFormElement("zoneId"), new RequiredFormElement("position"),
				new RequiredFormElement("fullname"), new RequiredFormElement("sortorder")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			StaffDirectoryContactBean contact = null;

			int contactId = form.getInt("contactId");

			if (contactId > 0) {
				contact = StaffDirectoryService.getStaffDirectoryContactBean(contactId);
			}
			else {
				contact = new StaffDirectoryContactBean();
			}

			contact.setDivision(StaffDirectoryContactBean.STAFF_DIRECTORY_DIVISION.get(form.getInt("divisionId")));
			contact.setZone(SchoolZoneService.getSchoolZoneBean(form.getInt("zoneId")));
			contact.setPosition(form.get("position"));
			contact.setFullName(form.get("fullname"));
			contact.setTelephone(form.get("telephone"));
			contact.setExtension(form.get("extension"));
			contact.setEmail(form.get("email"));
			contact.setFax(form.get("fax"));
			contact.setCellphone(form.get("cellphone"));
			contact.setSortorder(form.getInt("sortorder"));

			if (contact.getContactId() > 0) {
				StaffDirectoryService.updateStaffDirectoryContactBean(contact);
			}
			else {
				StaffDirectoryService.addStaffDirectoryContactBean(contact);
			}

			request.setAttribute("msgOK", "SUCCESS: Contact updated successfully.");

			path = "staff_directory.jsp";
		}
		else {
			path = "staff_directory.jsp";

			request.setAttribute("msgERR", "ERROR: Contact ID required for editing process.");
		}

		request.setAttribute("contacts", StaffDirectoryService.getStaffDirectoryContactBeans());

		return path;
	}

}
