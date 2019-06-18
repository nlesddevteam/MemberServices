package com.awsd.travel.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetClaimsApprovedByRegionRequestHandler extends RequestHandlerImpl {
	public GetClaimsApprovedByRegionRequestHandler() {
		
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		path = "claims_approved_by_region.jsp";
		return path;
	}
}