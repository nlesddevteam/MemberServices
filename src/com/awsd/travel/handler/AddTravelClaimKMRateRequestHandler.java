package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaimKMRateDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelClaimKMRateRequestHandler extends RequestHandlerImpl {

	public AddTravelClaimKMRateRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (request.getParameter("sdate") != null && request.getParameter("edate") != null) {
			request.setAttribute("claimrate",
					TravelClaimKMRateDB.getTravelClaimKMRate(request.getParameter("sdate"), request.getParameter("edate")));
			path = "add_km_rate.jsp";
		}
		else {
			path = "add_km_rate.jsp";
		}

		return path;
	}
}