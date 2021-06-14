package com.awsd.weather.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemDB;

public class MSViewWeatherCentralPrincipalAdminRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		SchoolSystem[] systems = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEATHERCENTRAL-PRINCIPAL-ADMINVIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		systems = SchoolSystemDB.getSchoolSystems(usr.getPersonnel());

		if ((systems != null) && (systems.length > 0))
			request.setAttribute("SchoolSystems", systems);

		path = "principaladmin.jsp";

		return path;
	}
}