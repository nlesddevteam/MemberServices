package com.awsd.weather.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.personnel.DistrictPersonnel;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemDB;
import com.awsd.weather.SchoolSystems;
import com.awsd.weather.WeatherCentralException;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;
import static java.lang.System.*;

public class MSSchoolSystemAdminRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String path;
		HttpSession session = null;
		User usr = null;
		String op = "";
		int ss_id, ss_admin;
		String[] ss_admin_bckup;
		String ss_name;
		String school_id[] = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		op = request.getParameter("op");
		
		if (op == null) {
			throw new WeatherCentralException("OP parameter is required.");
		}

		if (op.equalsIgnoreCase("add")) {
			ss_name = request.getParameter("ss_name");
			if (ss_name == null) {
				throw new WeatherCentralException("DESC parameter is required for ADD operation.");
			}

			if (request.getParameter("ss_admin") != null) {
				ss_admin = Integer.parseInt(request.getParameter("ss_admin"));
			}
			else {
				ss_admin = -1;
			}

			ss_admin_bckup = request.getParameterValues("ss_admin_bckup");

			school_id = request.getParameterValues("schools");
			if ((school_id == null) || (school_id.length == 0)) {
				throw new WeatherCentralException("SCHOOLS parameter is required for MOD operation.");
			}

			ss_id = SchoolSystemDB.addSchoolSystem(new SchoolSystem(ss_name.toUpperCase(), ss_admin, ss_admin_bckup),
					school_id);
			if (ss_id == -1) {
				throw new WeatherCentralException("Could not add School System to DB.");
			}

					SchoolSystems.reload();
			request.setAttribute("msgOK", "SUCCESS: School System successfully added.");
			path = "schoolsystemadmin.jsp";
		}
		else if (op.equalsIgnoreCase("del")) {

			if (request.getParameter("ss_id") != null) {
				ss_id = Integer.parseInt(request.getParameter("ss_id"));
			}
			else {
				throw new WeatherCentralException("ID parameter is required for DEL operation.");
			}

			SchoolSystemDB.deleteSchoolSystem(ss_id);

			SchoolSystems.reload();
			request.setAttribute("msgOK", "SUCCESS: School System successfully removed.");
			path = "schoolsystemadmin.jsp";
		}			
		

		else if (op.equalsIgnoreCase("mod")) {
			if (request.getParameter("ss_id") != null) {
				ss_id = Integer.parseInt(request.getParameter("ss_id"));
			}
			else {
				throw new WeatherCentralException("ID parameter is required for MOD operation.");
			}

			request.setAttribute("SCHOOLSYSTEM", SchoolSystemDB.getSchoolSystem(ss_id));
			request.setAttribute("PERSONNEL", new DistrictPersonnel());
			request.setAttribute("ALLSCHOOLS", DropdownManager.getSchools());			
			request.setAttribute("SELECTEDSCHOOLS",SchoolSystemDB.getSchoolSystemSchools(ss_id));

			path = "schoolsystemmodify.jsp";
		}
		
		else if (op.equalsIgnoreCase("mod-confirm")) {
			
			if (request.getParameter("schoolSystemID") != null) {
				ss_id = Integer.parseInt(request.getParameter("schoolSystemID"));
			}
			else {
				throw new WeatherCentralException("schoolSystemID parameter is required for MOD operation.");
			}

			if (request.getParameter("schoolSystemName") != null) {
				ss_name = request.getParameter("schoolSystemName");
			}
			else {
				throw new WeatherCentralException("schoolSystemName parameter is required for MOD operation.");
			}

			if (request.getParameter("schoolSystemAdmin.personnelID") != null) {
				ss_admin = Integer.parseInt(request.getParameter("schoolSystemAdmin.personnelID"));
			}
			else {
				throw new WeatherCentralException("schoolSystemAdmin.personnelID parameter is required for MOD operation.");
			}

			ss_admin_bckup = request.getParameterValues("schoolSystemAdminBackupAsIntArray");
			
			String schoollist = form.get("systemSchools");
			SchoolSystemDB.updateSchoolSystem(new SchoolSystem(ss_id, ss_name, ss_admin, ss_admin_bckup));
			//now we update the schools
			//remove old ones first
			SchoolSystemDB.removeSchoolSystemSchool(ss_id);
			//now parse the list
			for(String s : schoollist.split(",")) {
				SchoolSystemDB.addSchoolSystemSchool(ss_id, Integer.parseInt(s));
			}
			SchoolSystems.reload();
			request.setAttribute("msgOK", "SUCCESS: School System successfully updated.");
			path = "schoolsystemadmin.jsp";
		}
		else {
			path = "schoolsystemadmin.jsp";
		}

		return path;
	}
}