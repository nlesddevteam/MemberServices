package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.profile.ProfileDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewTravelClaimSystemRequestHandler extends RequestHandlerImpl {

	public ViewTravelClaimSystemRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (ProfileDB.getProfile(usr.getPersonnel()) == null)
			path = "index.jsp?noprofile=true";
		else
			path = "index.jsp";

		return path;
	}
}