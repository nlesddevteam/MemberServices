package com.awsd.navigation.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.UserPermissions;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.ldap.LDAPConnection;
import com.nlesd.ldap.LDAPServer;

public class MemberServicesLoginRequestHandler extends PublicAccessRequestHandlerImpl {

	public MemberServicesLoginRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("user", "FirstClass UserID is required."),
				new RequiredFormElement("pass", "FirstClass Password is required.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String userid = "";
		String password = "";

		boolean authenticated = true;
		boolean ldapConnected = false; //turns off registration.

		UserPermissions permissions = null;

		try {
			if (StringUtils.isNotEmpty(form.get("user")) && StringUtils.isNotEmpty(form.get("pass"))) {
				userid = form.get("user").toLowerCase();
				password = form.get("pass");

				try {
					// Contact FirstClass LDAP service
					if (userid.length() > 15) {
						userid = userid.substring(0, 15);
					}

					LDAPConnection ldap = new LDAPConnection(LDAPServer.PRODUCTION);

					ldap.authenticate(userid, password);

					usr = new User(userid, password);

				}
				catch (CommunicationException e) {
					// FirstClass Directory Services not available
					// Authenticate from database

					ldapConnected = false;

					System.err.println("LOGON SERVICES - FCDS unavailable, attempted database authentication [" + userid + "].");
					usr = User.authenticate(userid, password);
				}

				if (usr != null && usr.isValid()) {
					if (!usr.isRegistered() && ldapConnected) {
						System.err.println("LOGON SERVICES - Unregistered User [" + userid + "].");
						path = "register.jsp";
					}
					else if (!usr.isRegistered() && !ldapConnected) {
						request.setAttribute(
								"msg",
								"Registration is currently unavailable. If you previously had an esdnl.ca account try logging in with that account and following the account conversion process, if not please try again later.");
						authenticated = false;
					}
					else if (!isValidDomain(usr.getPersonnel().getEmailAddress())) {
						request.setAttribute("msg", "");
						session.setAttribute("usr", usr);

						return "convert_account.jsp";
					}
					else {
						System.err.println("LOGON SERVICES [" + (ldapConnected ? "FCDS" : "DB") + "] - " + "User Login @ "
								+ new SimpleDateFormat("MMMM dd, yyyy hh:mm a").format(Calendar.getInstance().getTime()) + " ["
								+ userid + "].");

						try {
							permissions = usr.getUserPermissions();
						}
						catch (SecurityException e) {
							System.err.println("LOGON SERVICES -  User has no assigned permissions [" + userid + "].");
						}
					}

					session.setAttribute("usr", usr);
				}
				else {
					request.setAttribute("msg", "Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
					authenticated = false;
				}
			}
			else {
				authenticated = false;
				if (request.getParameter("msg") != null)
					request.setAttribute("msg", request.getParameter("msg"));
			}
		}
		catch (SecurityException e) { // database authentication failed
			System.err.println("LOGIN SERVICES - Database authentication failed, " + e.getMessage() + " [" + userid + "].");

			request.setAttribute("msg", "Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
			authenticated = false;
		}
		catch (AuthenticationException e) { // FirstClass authentication failed
			System.err.println("LOGIN SERVICES - FCDS authentication failed, "
					+ ((e.getExplanation() != null) ? e.getExplanation() : e.getMessage()) + " [" + userid + "].");

			request.setAttribute("msg", "Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
			authenticated = false;

			try {
				// Contact FirstClass LDAP service
				if (userid.length() > 15) {
					userid = userid.substring(0, 15);
				}

				LDAPConnection ldap = new LDAPConnection(LDAPServer.ARCHIVE);

				ldap.authenticate(userid, password);

				//AUTHENTICATION SUCCESS ON OLD FIRSTCLASS SERVER
				usr = new User(userid, password);

				if (usr != null && usr.isValid() && usr.isRegistered()) {
					request.setAttribute("msg", "");
					session.setAttribute("usr", usr);

					return "convert_account.jsp";
				}
				else {
					request.setAttribute("msg", "Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
					authenticated = false;

					return "signin.jsp";
				}
			}
			catch (CommunicationException ce) { // FirstClass DS not available
				System.err.println("LOGIN SERVICES - FCDS-ARCHIVE unavailable, "
						+ ((ce.getExplanation() != null) ? ce.getExplanation() : ce.getMessage()) + " [" + userid + "].");
				request.setAttribute("msg", "Sign-in Error: FCDS-ARCHIVE unavailable. Please try again later.");
			}
			catch (AuthenticationException ae) {
				// authentication has already failed on main FCSD - DO NOTHING;
				System.err.println("LOGIN SERVICES - FCDS-ARCHIVE authentication failed, "
						+ ((ae.getExplanation() != null) ? ae.getExplanation() : ae.getMessage()) + " [" + userid + "].");

			}
			catch (NamingException ne) {
				System.err.println("LOGIN SERVICES - "
						+ ((ne.getExplanation() != null) ? ne.getExplanation() : ne.getMessage()) + " [" + userid + "].");
			}
		}
		catch (CommunicationException e) { // FirstClass DS not available
			System.err.println("LOGIN SERVICES - FCDS unavailable, "
					+ ((e.getExplanation() != null) ? e.getExplanation() : e.getMessage()) + " [" + userid + "].");

			request.setAttribute("msg", "FirstClass Server unavailable for authentication.");
			authenticated = false;
		}
		catch (NamingException e) {
			System.err.println("LOGIN SERVICES - " + ((e.getExplanation() != null) ? e.getExplanation() : e.getMessage())
					+ " [" + userid + "].");

			request.setAttribute("msg", "Member Services is currently offline. Please try again later.");
			authenticated = false;
		}

		if (usr == null || !usr.isValid() || !authenticated) {
			path = "signin.jsp";
		}
		else if (usr.isValid() && usr.isRegistered() && authenticated) {
			// record login time.
			usr.loggedOn();

			String app = usr.getPersonnel().getViewOnNextLogon();
			if (app != null) {
				if (app.equalsIgnoreCase("PROFILE")) {
					if (permissions.containsKey("PERSONNEL-PROFILE-TEACHER-VIEW")) {
						path = "Profile/Teacher/teacher_viewprofile_name.jsp";
					}
				}
				request.setAttribute("REDIRECT", new Boolean("true"));
			}
			else {
				path = "memberservices_frame.jsp";
			}
		}

		return path;
	}

	private boolean isValidDomain(String email) {

		return email.indexOf("@nlesd.ca") > 0;
	}

}