package com.awsd.navigation.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.awsd.personnel.Personnel;
import com.awsd.security.IllegalAccessAttemptException;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.UserPermissions;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import net.sf.json.JSONObject;

public class GoogleLoginAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	static final String CLIENT_ID = "362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com";
	static final List<String> SCOPES = Arrays.asList("email", "profile");
	static final String CLIENT_SECRET = "wLktUnFs0HWo8Y6h_KRPM8iQ";

	public GoogleLoginAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id_token")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (this.validate_form()) {
				HttpTransport transport = new NetHttpTransport();
				JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
				GoogleIdTokenVerifier verifier = (new GoogleIdTokenVerifier.Builder(transport, jsonFactory)).setAudience(
						Arrays.asList(CLIENT_ID)).build();

				GoogleIdToken idToken = null;
				
				try {
					idToken = verifier.verify(form.get("id_token"));
				}
				catch (Exception e) {
					//throw new Exception(e.getMessage());
					System.out.println(e.getMessage());
				}

				if (idToken != null) {
					Payload payload = idToken.getPayload();

					if (payload == null) {
						throw new IllegalAccessAttemptException("Invalid payload. Illegal Access Attempt!");
					}

					if (!StringUtils.equals("nlesd.ca", (String) payload.getHostedDomain())) {
						throw new IllegalAccessAttemptException("Invalid domain [" + payload.getHostedDomain()
								+ "] attempted access.");
					}

					// Get profile information from payload
					String email = payload.getEmail();
					if (email.toLowerCase().indexOf("_s@") >= 0) {
						throw new IllegalAccessAttemptException("Student access attempt [" + email + "].");
					}

					JSONObject json = new JSONObject();

					UserPermissions permissions = null;
					User usr = new User(email);

					if (usr != null && usr.isValid()) {
						if (!usr.isRegistered()) {
							System.err.println("LOGON SERVICES - Unregistered User [" + email + "].");

							json.put("success", true);

							JSONObject user = new JSONObject();
							user.put("email", email);
							user.put("firstname", payload.get("given_name"));
							user.put("lastname", payload.get("family_name"));

							json.put("user", user);

							json.put("redirect", "register.jsp");

							session.setAttribute("REGISTATION-GOOGLE-PRELOADED-DATA", json);
						}
						else {
							System.err.println("LOGON SERVICES [GOOGLE] - " + "User Login @ "
									+ new SimpleDateFormat("MMMM dd, yyyy hh:mm a").format(Calendar.getInstance().getTime()) + " ["
									+ email + " *"
									+ (usr.getPersonnel() != null && usr.getPersonnel().getPersonnelCategory() != null
											? usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()
											: "UNKNOWN")
									+ "*].");

							session.setAttribute("usr", usr);
							session.setAttribute("gmailicon", form.get("picture_u"));

							try {
								permissions = usr.getUserPermissions();
							}
							catch (SecurityException e) {
								System.err.println("LOGON SERVICES -  User has no assigned permissions [" + email + "].");
							}
						}
					}
					else {
						throw new SecurityException("Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
					}

					if (usr == null || !usr.isValid()) {
						throw new SecurityException("Sign-in Error: Your user id  and/or password is incorrect. Please try again.");
					}
					else if (usr.isValid() && usr.isRegistered()) {
						// record login time.
						usr.loggedOn();

						if (StringUtils.isNotBlank(usr.getPersonnel().getPassword())) {
							Personnel p = usr.getPersonnel();

							p.setUserName(p.getEmailAddress());
							p.setPassword(null);
						}

						String app = usr.getPersonnel().getViewOnNextLogon();
						if (app != null) {
							if (app.equalsIgnoreCase("PROFILE")) {
								if (permissions.containsKey("PERSONNEL-PROFILE-TEACHER-VIEW")) {
									json.put("redirect", "Profile/Teacher/index.jsp");
								}
							}
						}
						else {
							json.put("redirect", "memberservices_frame.jsp");
						}

						json.put("success", true);

						JSONObject user = new JSONObject();
						user.put("email", email);

						json.put("user", user);
					}
					else {
						if (!json.containsKey("success")) {
							json.put("success", false);
						}
					}

					PrintWriter out = response.getWriter();

					response.setContentType("text/json");
					response.setHeader("Cache-Control", "no-cache");
					out.write(json.toString());
					out.flush();
					out.close();
				}
				else {
					throw new SecurityException("Invalid ID token.");
				}
			}
			else {
				throw new SecurityException("Invalid ID token.");
			}
		}
		catch (SecurityException e) {
			JSONObject json = new JSONObject();

			json.put("success", false);
			json.put("error", e.getMessage());

			if (e instanceof IllegalAccessAttemptException) {
				json.put("logout", true);
			}

			PrintWriter out = response.getWriter();

			response.setContentType("text/json");
			response.setHeader("Cache-Control", "no-cache");
			out.write(json.toString());
			out.flush();
			out.close();
		}

		return null;
	}
}
