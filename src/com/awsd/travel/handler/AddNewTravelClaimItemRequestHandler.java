package com.awsd.travel.handler;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;

public class AddNewTravelClaimItemRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		TravelClaim claim = TravelClaimDB.getClaim(id);
		//now we figure out the start and end date for the claim month
		//split the year
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
		request.setAttribute("claimid", id);
		return "add_new_claim_item.jsp";
	}
}