package com.esdnl.roer.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.SMTPAuthenticatedMail;
import com.awsd.personnel.profile.Profile;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.roer.ROERequest;
import com.esdnl.roer.ROERequestDB;
import com.esdnl.roer.UnpaidDay;

public class AddROERequestRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		ROERequest roer = null;
		Profile profile = null;
		SimpleDateFormat sdf = null;
		StringTokenizer str_tok = null;
		Vector dates = null;
		UnpaidDay uday = null;
		String arr[] = null;
		String token = "";
		String path = "";
		SMTPAuthenticatedMail smtp = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("ROEREQUEST-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("ADDED")) {
				profile = new Profile(usr.getPersonnel(), request.getParameter("cur_street_addr"), request.getParameter("cur_community"), request.getParameter("cur_province"), request.getParameter("cur_postal_code"), request.getParameter("home_phone"), request.getParameter("fax"), request.getParameter("cell_phone"), request.getParameter("gender"), request.getParameter("sin"));

				if (ProfileDB.getProfile(usr.getPersonnel()) == null) {
					ProfileDB.addProfile(profile);
				}
				else {
					ProfileDB.updateProfile(profile);
				}

				try {
					sdf = new SimpleDateFormat("dd/MM/yyyy");

					System.err.println("First Day Worked: " + request.getParameter("first_day_worked"));
					System.err.println("Last Day Worked: " + request.getParameter("last_day_worked"));
					System.err.println("Birth Date: " + request.getParameter("birth_date"));
					System.err.println("Replacement Start Date: " + request.getParameter("replacement_start_date"));
					System.err.println("Replacement Finish Date: " + request.getParameter("replacement_finish_date"));
					System.err.println("Last Record Issued Date: " + request.getParameter("last_record_issued_date"));

					roer = new ROERequest(usr.getPersonnel(), null, null, sdf.parse(request.getParameter("first_day_worked")), sdf.parse(request.getParameter("last_day_worked")), Double.parseDouble(request.getParameter("week1")), Double.parseDouble(request.getParameter("week2")), Double.parseDouble(request.getParameter("week3")), Double.parseDouble(request.getParameter("week4")), request.getParameter("reason_for_record"), ((request.getParameter("birth_date") != null) && !request.getParameter(
							"birth_date").trim().equals("")) ? sdf.parse(request.getParameter("birth_date")) : null, ((request.getParameter("replacement_start_date") != null) && !request.getParameter(
							"replacement_start_date").trim().equals("")) ? sdf.parse(request.getParameter("replacement_start_date"))
							: null, ((request.getParameter("replacement_finish_date") != null) && !request.getParameter(
							"replacement_finish_date").trim().equals("")) ? sdf.parse(request.getParameter("replacement_finish_date"))
							: null, sdf.parse(request.getParameter("last_record_issued_date")));

					if ((request.getParameter("sick_dates") != null) && !request.getParameter("sick_dates").equals("")) {
						str_tok = new StringTokenizer(request.getParameter("sick_dates"), " ");

						if (str_tok.hasMoreTokens()) {
							dates = new Vector(5);

							while (str_tok.hasMoreTokens()) {
								token = str_tok.nextToken();
								if ((token != null) && !token.equals(""))
									dates.add(sdf.parse(token.trim()));
							}

							roer.setSickLeaveDates(dates);
						}
					}

					if ((request.getParameter("unpaid_dates") != null) && !request.getParameter("unpaid_dates").equals("")) {
						str_tok = new StringTokenizer(request.getParameter("unpaid_dates"), "|");

						if (str_tok.hasMoreTokens()) {
							dates = new Vector(5);

							while (str_tok.hasMoreTokens()) {
								arr = str_tok.nextToken().split("-");

								uday = new UnpaidDay(sdf.parse(arr[0].trim()), Integer.parseInt(arr[1].trim()));
								dates.add(uday);
							}

							roer.setUnpaidDates(dates);
						}
					}

					ROERequestDB.addRequest(roer);

					request.setAttribute("msg", "ROE Request submitted SUCCESSFULLY");
					request.setAttribute("CURRENT_PROFILE", profile);
					request.setAttribute("ROER", roer);

					try {
						smtp = new SMTPAuthenticatedMail("mail.esdnl.ca", "ms", "services");

						smtp.postMail(
								new String[] {
									usr.getPersonnel().getEmailAddress()
								},
								null,
								null,
								"ROE Request Recieved",
								usr.getPersonnel().getFullNameReverse()
										+ ", <br><br> Your ROE Request has been receieved and queued for processing. You will receive a second email once your request has been processed. <br><br>Thank you."
										+ "<br><br>PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services", "ms@nlesd.ca");
					}
					catch (Exception e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Claim submitted, supervisory email not sent.");
					}

					path = "index.jsp?op=VIEW";
				}
				catch (ParseException e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "ROE Request could not be submitted. Please contact administrator.");
					request.setAttribute("CURRENT_PROFILE", profile);
					path = "index.jsp";
				}
				catch (SQLException e) {
					System.err.println(e);
					request.setAttribute("msg", "ROE Request could not be submitted. Please contact administrator.");
					request.setAttribute("CURRENT_PROFILE", profile);
					path = "index.jsp";
				}
			}
			else {
				profile = ProfileDB.getProfile(usr.getPersonnel());
				request.setAttribute("CURRENT_PROFILE", profile);
				path = "index.jsp";
			}
		}
		else {
			profile = ProfileDB.getProfile(usr.getPersonnel());
			request.setAttribute("CURRENT_PROFILE", profile);
			path = "index.jsp";
		}

		return path;
	}
}