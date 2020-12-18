package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.Personnel;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class SupervisorViewPreviousClaimsRequestHandler extends RequestHandlerImpl {

	public SupervisorViewPreviousClaimsRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-SUPERVISOR-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		Personnel p = null;
		int sid=-1;
		Vector<TravelClaim> list = null;
		try {
			
			sid = Integer.parseInt(request.getParameter("sid"));
		}
		catch (NumberFormatException e) {
			sid = -1;
		}
		if(sid >0)
		{
			list= TravelClaimDB.getClaimsSupervisorStatus(usr.getPersonnel(),sid);
		}else {
			list= TravelClaimDB.getClaimsSupervisorPrevious(usr.getPersonnel());
		}
		
		request.setAttribute("claimslist", list);
		path = "supervisor_previous_claims.jsp";
		return path;

	}
}
