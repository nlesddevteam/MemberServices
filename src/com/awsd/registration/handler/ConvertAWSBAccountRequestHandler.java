package com.awsd.registration.handler;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.ldap.LDAPConnection;
import com.nlesd.ldap.LDAPServer;

public class ConvertAWSBAccountRequestHandler extends RequestHandlerImpl {

	public ConvertAWSBAccountRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("firstname", "First name required for account conversion."),
				new RequiredFormElement("lastname", "Last name required for account conversion."),
				new RequiredFormElement("emailaddr", "Email required for account conversion."),
				new RequiredFormElement("uid", "Firstclass userid required for account conversion."),
				new RequiredFormElement("password", "Password required for account conversion."),
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (!usr.isRegistered()) {
			path = "register.jsp";
		}
		else if (usr.getPersonnel().getEmailAddress().indexOf("@nlesd.ca") >= 0) {
			path = "memberservices_frame.jsp";
		}
		else {

			if (validate_form()) {
				String userid = form.get("uid").toLowerCase();

				try {
					LDAPConnection ldap = new LDAPConnection(LDAPServer.PRODUCTION);

					if (userid.length() > 15) {
						userid = userid.substring(0, 15);
					}

					ldap.authenticate(userid, form.get("password"));

					Personnel p = usr.getPersonnel();

					p.setFirstName(form.get("firstname"));
					p.setLastName(form.get("lastname"));
					p.setUserName(userid);
					p.setFirstClassPassword(form.get("password"));
					p.setEmailAddress(form.get("emailaddr"));

					PersonnelDB.updatePersonnel(p);

					usr.setPersonnel(p);

					session.setAttribute("usr", usr);

					System.err.println("ACCOUNT CONVERTED: " + usr.getLotusUserFullNameReverse() + "[" + usr.getUsername()
							+ "] TO " + userid);

					path = "memberservices_frame.jsp";
				}
				catch (AuthenticationException e) { //firstclass failure
					System.err.println(userid + ":" + ((e.getExplanation() != null) ? e.getExplanation() : e.getMessage()));

					request.setAttribute("msg", "Your user id  and/or password is incorrect. Please try again.");
					path = "convert_account.jsp";
				}
				catch (CommunicationException e) { //firstclass failure
					System.err.println(e.getMessage());

					request.setAttribute("msg", "FirstClass Server not responding.");
					path = "convert_account.jsp";
				}
				catch (NamingException e) {
					System.err.println(e.getMessage());

					request.setAttribute("msg", "Member Services is currently offline. Please try again later.");
					path = "convert_account.jsp";
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());
				path = "convert_account.jsp";
			}
		}

		return path;
	}
}