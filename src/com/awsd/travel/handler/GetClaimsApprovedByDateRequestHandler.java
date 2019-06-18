package com.awsd.travel.handler;

import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetClaimsApprovedByDateRequestHandler extends RequestHandlerImpl {
	public GetClaimsApprovedByDateRequestHandler() {
		
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		TreeMap<String,String>treemap = TravelClaimDB.getClaimsApprovedDates();
		request.setAttribute("approveddates", treemap);
		path = "claims_approved_by_date.jsp";
		return path;
	}
}