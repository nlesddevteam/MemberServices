package com.esdnl.mrs.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.mrs.MaintenanceRequest;
import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.mrs.RequestType;
import com.esdnl.mrs.RequestTypeDB;
import com.esdnl.mrs.StatusCode;

public class AddRequestRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		School s = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")
					|| usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW") || usr.getUserPermissions().containsKey(
					"MAINTENANCE-ADMIN-REGIONAL-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {

				if ((usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW") || usr.getUserPermissions().containsKey(
						"MAINTENANCE-ADMIN-REGIONAL-VIEW"))
						&& ((request.getParameter("school") == null) || (request.getParameter("school").equals("-1")))) {
					request.setAttribute("msg", "Please choose SCHOOL.");
				}
				if ((request.getParameter("request_type") == null) || (request.getParameter("request_type").equals("-1"))) {
					request.setAttribute("msg", "Please choose REQUEST TYPE.");
				}
				else if ((request.getParameter("rname_num") == null) || (request.getParameter("rname_num").equals(""))) {
					request.setAttribute("msg", "Please specify ROOM NAME/NUMBER.");
				}
				else if ((request.getParameter("request_priority") == null)
						|| (request.getParameter("request_priority").equals("-1"))) {
					request.setAttribute("msg", "Please choose REQUEST PRIORITY.");
				}
				else if ((request.getParameter("request_desc") == null) || (request.getParameter("request_desc").equals(""))) {
					request.setAttribute("msg", "Please specify REQUEST DESCRIPTION.");
				}
				else {
					if (usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")
							|| usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW")) {
						s = SchoolDB.getSchool(Integer.parseInt(request.getParameter("school")));
					}
					else {
						s = usr.getPersonnel().getSchool();
					}

					if (MaintenanceRequestDB.addMaintenanceRequest(new MaintenanceRequest(new RequestType(request.getParameter("request_type")), new StatusCode("UNASSIGNED"), request.getParameter("request_desc"), Calendar.getInstance().getTime(), request.getParameter("rname_num"), usr.getPersonnel(), s, Integer.parseInt(request.getParameter("request_priority")))) > 0) {
						request.setAttribute("msg", "REQUEST SUBMITTED SUCCESSFULLY!");
					}
					else {
						request.setAttribute("msg", "REQUEST COULD NOT BE SUBMITTED!");
					}
				}

				request.setAttribute("REQUEST_TYPES", (RequestType[]) RequestTypeDB.getRequestTypes().toArray(
						new RequestType[0]));

				if (usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW")) {
					request.setAttribute("SCHOOLS",
							(School[]) MaintenanceRequestDB.getRegionalSchools(usr.getPersonnel()).toArray(new School[0]));
					request.setAttribute("OUTSTANDING_REQUESTS", new Integer(0));
				}
				else if (usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")) {
					request.setAttribute("SCHOOLS", (School[]) SchoolDB.getSchools().toArray(new School[0]));
					request.setAttribute("OUTSTANDING_REQUESTS", new Integer(0));
				}
				else {
					request.setAttribute("OUTSTANDING_REQUESTS",
							new Integer(((MaintenanceRequest[]) MaintenanceRequestDB.getOutstandingMaintenanceRequests(
									usr.getPersonnel().getSchool()).get(0)).length));
				}

				path = "add_request.jsp";
			}
			else {
				path = "error_message.jsp";
				request.setAttribute("msg", "INVALID OPTION");
			}
		}
		else {
			request.setAttribute("REQUEST_TYPES", (RequestType[]) RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));

			if (usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-REGIONAL-VIEW")) {
				request.setAttribute("SCHOOLS", (School[]) MaintenanceRequestDB.getRegionalSchools(usr.getPersonnel()).toArray(
						new School[0]));
				request.setAttribute("OUTSTANDING_REQUESTS", new Integer(0));
			}
			else if (usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")) {
				request.setAttribute("SCHOOLS", (School[]) SchoolDB.getSchools().toArray(new School[0]));
				request.setAttribute("OUTSTANDING_REQUESTS", new Integer(0));
			}
			else {
				request.setAttribute("OUTSTANDING_REQUESTS",
						new Integer(((MaintenanceRequest[]) MaintenanceRequestDB.getOutstandingMaintenanceRequests(
								usr.getPersonnel().getSchool()).get(0)).length));
			}

			path = "add_request.jsp";
		}

		return path;
	}
}