package com.nlesd.webmaint.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.webmaint.bean.StaffDirectoryContactBean;

public class AddStaffDirectoryContactRequestHandler extends RequestHandlerImpl {

	public AddStaffDirectoryContactRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW","WEBMAINTENANCE-STAFFING"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			request.setAttribute("contact", new StaffDirectoryContactBean());

			path = "staff_directory_edit.jsp";
		}
		else {
			path = "staff_directory.jsp";

			request.setAttribute("msg", "Could not initiate the add staff directory contact process.");
		}

		return path;
	}

}
