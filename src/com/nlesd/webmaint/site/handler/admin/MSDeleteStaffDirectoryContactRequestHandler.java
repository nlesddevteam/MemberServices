package com.nlesd.webmaint.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.webmaint.bean.StaffDirectoryContactBean;
import com.nlesd.webmaint.service.StaffDirectoryService;

public class MSDeleteStaffDirectoryContactRequestHandler extends RequestHandlerImpl {

	public MSDeleteStaffDirectoryContactRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW","WEBMAINTENANCE-STAFFING"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			StaffDirectoryContactBean contact = StaffDirectoryService.getStaffDirectoryContactBean(form.getInt("id"));

			if (contact != null) {
				StaffDirectoryService.deleteStaffDirectoryContactBean(contact);

				request.setAttribute("msgOK", "SUCCESS: Contact [" + contact.getFullName() + "] deleted.");
			}
			else {
				request.setAttribute("msgERR", "ERROR: Contact with ID [" + form.getInt("id") + "] could not be found.");
			}
		}
		else {
			request.setAttribute("msgERR", "ERROR: Contact ID required for editting process.");
		}

		path = "staff_directory.jsp";

		return path;
	}

}
