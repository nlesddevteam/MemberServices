package com.nlesd.school.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewSchoolRequestHandler extends RequestHandlerImpl {

	public ViewSchoolRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW","WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL","WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY","WEBMAINTENANCE-SCHOOLPROFILE-ADMIN"
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

			request.setAttribute("school", SchoolDB.getSchoolFullDetails(form.getInt("id")));

			
			if((usr.checkRole("BUSROUTE-POST"))) {				
				path = "/WebUpdateSystem/BusRoutes/school_directory_bus_routes.jsp";
				
			} else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL")) || (usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY"))){				
				path = "/WebUpdateSystem/SchoolProfiles/school_profile.jsp";
			
			} else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-ADMIN")) && (!usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL"))){				
				path = "/WebUpdateSystem/SchoolProfiles/school_profile.jsp";			
				
			} else {
				
				path = "school_profile.jsp";
			}
			
			
		}
		else {
			path = "school_profile.jsp";

			request.setAttribute("msg", "School ID required for editting process.");
		}

		return path;
	}

}
