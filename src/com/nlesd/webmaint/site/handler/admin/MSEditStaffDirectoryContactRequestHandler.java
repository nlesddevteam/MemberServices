package com.nlesd.webmaint.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.webmaint.service.StaffDirectoryService;

public class MSEditStaffDirectoryContactRequestHandler extends RequestHandlerImpl {

	public MSEditStaffDirectoryContactRequestHandler() {

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

			request.setAttribute("contact", StaffDirectoryService.getStaffDirectoryContactBean(form.getInt("id")));

			path = "staff_directory_edit.jsp";
			
			
		}
		else {
			path = "staff_directory.jsp";

			request.setAttribute("msgERR", "ERROR: Contact ID required for editing process.");
		}

		return path;
	}

}
