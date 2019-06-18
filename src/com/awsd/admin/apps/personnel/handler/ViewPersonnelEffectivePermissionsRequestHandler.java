package com.awsd.admin.apps.personnel.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.Role;
import com.awsd.security.RoleDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewPersonnelEffectivePermissionsRequestHandler extends RequestHandlerImpl {

	public ViewPersonnelEffectivePermissionsRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("pid")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			Personnel p = PersonnelDB.getPersonnel(form.getInt("pid"));

			if (form.exists("rid")) {
				Role r = RoleDB.getRole(form.get("rid"));
				if (r != null)
					RoleDB.deleteRoleMembership(r, p);
			}

			Map<String, Role> roles = RoleDB.getRoles(p);

			request.setAttribute("prec", p);
			request.setAttribute("roles", roles);
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
		}

		return "personnel_admin_effective_permissions.jsp";
	}
}