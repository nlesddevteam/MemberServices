package com.awsd.weather.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.weather.ClosureStatus;
import com.awsd.weather.ClosureStatusDB;
import com.awsd.weather.WeatherCentralException;

public class ClosureStatusAdminRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		String op = "";
		int sid;
		String desc;

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
			desc = request.getParameter("status_desc");
			if (desc != null) {
				ClosureStatusDB.addClosureStatus(desc);
			}
			else {
				throw new WeatherCentralException("DESC parameter is required for ADD operation.");
			}
		}
		else if (op.equalsIgnoreCase("del")) {

			if (request.getParameter("status_id") != null) {
				sid = Integer.parseInt(request.getParameter("status_id"));
			}
			else {
				throw new WeatherCentralException("ID parameter is required for MOD operation.");
			}

			ClosureStatusDB.deleteClosureStatus(sid);
		}
		else if (op.equalsIgnoreCase("mod")) {
			desc = request.getParameter("status_desc");
			if (desc == null) {
				throw new WeatherCentralException("DESC parameter is required for MOD operation.");
			}

			if (request.getParameter("status_id") != null) {
				sid = Integer.parseInt(request.getParameter("status_id"));
			}
			else {
				throw new WeatherCentralException("ID parameter is required for MOD operation.");
			}

			ClosureStatusDB.updateClosureStatus(new ClosureStatus(sid, desc));
		}

		path = "closurestatusadmin.jsp";

		return path;
	}
}