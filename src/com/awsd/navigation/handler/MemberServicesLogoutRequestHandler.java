package com.awsd.navigation.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.servlet.RequestHandler;

public class MemberServicesLogoutRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;

		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("ADMIN_LOGIN_AS") != null) {

				session.setAttribute("usr", session.getAttribute("ADMIN_USR"));
				session.removeAttribute("ADMIN_LOGIN_AS");
				session.removeAttribute("ADMIN_USR");

				path = "memberservices_frame.jsp";
			}
			else {
				session.removeAttribute("usr");
				session.invalidate();

				path = "signin.jsp";
			}
		}
		else {
			path = "signin.jsp";
		}

		return path;
	}
}