package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelCategoryDB;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.profile.Profile;
import com.awsd.personnel.profile.ProfileDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class MyProfileRequestHandler extends RequestHandlerImpl {

	public MyProfileRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String op;
		Profile profile = null;

		op = request.getParameter("op");
		if (op != null) {
			if (op.equalsIgnoreCase("ADDED") || op.equalsIgnoreCase("UPDATED")) {
				if ((request.getParameter("cur_street_addr") != null)
						&& (!request.getParameter("cur_street_addr").trim().equals(""))) {
					if ((request.getParameter("cur_community") != null)
							&& (!request.getParameter("cur_community").trim().equals(""))) {
						if ((request.getParameter("cur_province") != null)
								&& (!request.getParameter("cur_province").trim().equals(""))) {
							if ((request.getParameter("cur_postal_code") != null)
									&& (!request.getParameter("cur_postal_code").trim().equals(""))) {
								if ((request.getParameter("home_phone") != null)
										&& (!request.getParameter("home_phone").trim().equals(""))) {
									if ((request.getParameter("gender") != null) && (!request.getParameter("gender").trim().equals(""))) {
										if ((request.getParameter("position") != null)
												&& (!request.getParameter("position").trim().equals(""))) {
											profile = new Profile(usr.getPersonnel(), request.getParameter(
													"cur_street_addr"), request.getParameter("cur_community"), request.getParameter(
															"cur_province"), request.getParameter("cur_postal_code"), request.getParameter(
																	"home_phone"), request.getParameter(
																			"fax"), request.getParameter("cell_phone"), request.getParameter("gender"));
											if (op.equalsIgnoreCase("ADDED")) {
												profile = ProfileDB.addProfile(profile);
											}
											else if (op.equalsIgnoreCase("UPDATED")) {
												profile = ProfileDB.updateProfile(profile);
											}
											request.setAttribute("CURRENT_PROFILE", profile);
											usr.getPersonnel().setPersonnelCategory(
													PersonnelCategoryDB.getPersonnelCategory(Integer.parseInt(request.getParameter("position"))));
										}
										else
											request.setAttribute("msg", "Position required.");
									}
									else
										request.setAttribute("msg", "Gender required.");
								}
								else
									request.setAttribute("msg", "Home Phone Number required.");
							}
							else
								request.setAttribute("msg", "Postal Code required.");
						}
						else
							request.setAttribute("msg", "Province required.");
					}
					else
						request.setAttribute("msg", "City/Town required.");
				}
				else
					request.setAttribute("msg", "Street Address required.");
				path = "profile.jsp";
				if (profile != null) {
					path += "?op=update";
				}
			}
			else if (op.equalsIgnoreCase("VIEW")) {
				if (request.getParameter("id") != null) {
					Personnel p = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("id")));
					profile = ProfileDB.getProfile(p);
					if (profile != null) {
						request.setAttribute("CURRENT_PROFILE", profile);
						path = "profile.jsp?op=view";
					}
					else {
						path = "claim_error.jsp?msg=No Profile on record for " + p.getFullNameReverse() + ". ";
					}
				}
				else {
					path = "claim_error.jsp?msg=You cannot view the requested profile.";
				}
			}
			else {
				request.setAttribute("msg", "Invalid option.");
				path = "profile.jsp";
			}
		}
		else {
			path = "profile.jsp";
			profile = ProfileDB.getProfile(usr.getPersonnel());
			if (profile != null) {
				request.setAttribute("CURRENT_PROFILE", profile);
				path += "?op=update";
			}
		}

		return path;
	}
}