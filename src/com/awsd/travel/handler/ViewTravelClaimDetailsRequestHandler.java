package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimStatus;
import com.awsd.travel.service.TravelBudgetService;


public class ViewTravelClaimDetailsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";

		TravelClaim claim = null;
		int id = -1;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				if ((usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID())
						|| usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")
						|| usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")
						|| usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SEARCH")) {

					request.setAttribute("SUPERVISOR", "true");
					if ((claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED) || claim.getCurrentStatus().equals(
							TravelClaimStatus.REVIEWED))
							&& !usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")) {
						claim.setCurrentStatus(usr.getPersonnel(), TravelClaimStatus.REVIEWED);
					}
					String[] years = claim.getFiscalYear().split("-");
					if(claim.getFiscalMonth() < 6){
						request.setAttribute("fiscalyear", Integer.parseInt(years[1]));
						request.setAttribute("fiscalmonth", claim.getFiscalMonth());
						Calendar monthstart = new GregorianCalendar(Integer.parseInt(years[1]),claim.getFiscalMonth(),1);
						request.setAttribute("lastdaymonth",monthstart.getActualMaximum(Calendar.DAY_OF_MONTH));
					}else{
						request.setAttribute("fiscalyear", Integer.parseInt(years[0]));
						request.setAttribute("fiscalmonth", claim.getFiscalMonth());
						Calendar monthstart = new GregorianCalendar(Integer.parseInt(years[0]),claim.getFiscalMonth(),1);
						request.setAttribute("lastdaymonth",monthstart.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
					request.setAttribute("TRAVELCLAIM", claim);
					
					path = "claim_details.jsp";
				}
				else if (usr.getPersonnel().getPersonnelID() == claim.getPersonnel().getPersonnelID()) {
					request.setAttribute("TRAVELCLAIM", claim);
					String[] years = claim.getFiscalYear().split("-");
					if(claim.getFiscalMonth() < 6){
						request.setAttribute("fiscalyear", Integer.parseInt(years[1]));
						request.setAttribute("fiscalmonth", claim.getFiscalMonth());
						Calendar monthstart = new GregorianCalendar(Integer.parseInt(years[1]),claim.getFiscalMonth(),1);
						request.setAttribute("lastdaymonth",monthstart.getActualMaximum(Calendar.DAY_OF_MONTH));
					}else{
						request.setAttribute("fiscalyear", Integer.parseInt(years[0]));
						request.setAttribute("fiscalmonth", claim.getFiscalMonth());
						Calendar monthstart = new GregorianCalendar(Integer.parseInt(years[0]),claim.getFiscalMonth(),1);
						request.setAttribute("lastdaymonth",monthstart.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
					path = "claim_details.jsp";
				}
				else {
					path = "claim_error.jsp?msg=You do not have permission to view this travel claim.";
				}

				request.setAttribute("TOTAL_CLAIMED",
						new Double(TravelClaimDB.getYearToDateTotalClaimed(claim.getPersonnel(), claim.getFiscalYear())));
				request.setAttribute("BUDGET", TravelBudgetService.getTravelBudget(claim.getPersonnel(), claim.getFiscalYear()));
				
				
			}
			else {
				path = "claim_error.jsp?msg=Claim cound not be found.";
			}
		}
		else
			path = "claim_error.jsp?msg=Claim cound not be found.";

		return path;
	}
}