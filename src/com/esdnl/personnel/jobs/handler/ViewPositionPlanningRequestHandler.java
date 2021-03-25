package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.personnel.jobs.dao.MyHrpSettingsManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewPositionPlanningRequestHandler extends RequestHandlerImpl {

	public ViewPositionPlanningRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW") || usr.checkPermission("PERSONNEL-VICEPRINCIPAL-VIEW")) {

			if (MyHrpSettingsManager.getMyHrpSettings().isPpBlockSchools()) {
				return "admin_index.jsp";
			}

			School s = usr.getPersonnel().getSchool();

			try {
				LocationBean loc = LocationManager.getLocationBeanByDeptId(usr.getPersonnel().getSchool().getSchoolDeptID());

				/*
				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(
						StringUtils.getSchoolYear(new Date()), loc.getLocationId());
				
				request.setAttribute("allocation", allocation);
				*/

				request.setAttribute("location", loc);

				path = "admin_position_planning.jsp";
			}
			catch (LocationException e) {
				request.setAttribute("msg", "No SDS location could be found associated with Dept ID " + s.getSchoolDeptID());
				path = "index.jsp";
			}
			/*
			catch (JobOpportunityException e) {
				request.setAttribute("msg", e.getMessage());
				path = "index.jsp";
			}
			*/
		}
		else if (usr.checkPermission("PERSONNEL-ADMIN-VIEW")) {
			path = "admin_position_planning.jsp";
		}
		else
			path = "index.jsp";

		return path;
	}
}
