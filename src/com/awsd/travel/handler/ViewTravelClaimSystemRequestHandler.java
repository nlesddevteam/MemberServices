package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.crypto.PasswordEncryption;
import com.awsd.servlet.RequestHandler;

public class ViewTravelClaimSystemRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		String username = "";
		String hash = "";
		Personnel p = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			// System.err.println("NOT NULL");

			usr = (User) session.getAttribute("usr");
			// System.err.println(usr.getUsername());
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			try {
				// System.err.println("HELLO HELLO");
				username = request.getParameter("u");
				if ((username == null) || (username.equals(""))) {
					System.err.println("NO USERNAME");
					throw new SecurityException("User login required.");
				}

				hash = request.getParameter("p");
				if ((hash == null) || (hash.equals(""))) {
					System.err.println("NO PASSWORD");
					throw new SecurityException("User login required.");
				}

				p = PersonnelDB.getPersonnel(username);
				if (p == null) {
					System.err.println("NO PREC");
					throw new SecurityException("User login required.");
				}

				if (!p.getPassword().equals(PasswordEncryption.decrypt(hash))) {
					System.err.println("PASSWORD NOT EQUAL");
					throw new SecurityException("User login required.");
				}

				usr = new User(username, p.getPassword());
				session = request.getSession(true);
				session.setAttribute("usr", usr);
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				throw new SecurityException("User login required.");
			}
		}

		if (ProfileDB.getProfile(usr.getPersonnel()) == null)
			path = "index.jsp?noprofile=true";
		else
			path = "index.jsp";

		return path;
	}
}