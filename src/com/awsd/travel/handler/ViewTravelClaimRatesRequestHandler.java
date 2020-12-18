package com.awsd.travel.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaimKMRate;
import com.awsd.travel.TravelClaimKMRateDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewTravelClaimRatesRequestHandler extends RequestHandlerImpl {

	public ViewTravelClaimRatesRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		ArrayList<TravelClaimKMRate> rates = TravelClaimKMRateDB. getTravelClaimKMRates();
		request.setAttribute("RATES", rates);
		path = "travel_rates.jsp";
		return path;
	}
}
