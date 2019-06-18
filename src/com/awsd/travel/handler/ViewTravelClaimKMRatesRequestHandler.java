package com.awsd.travel.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaimKMRate;
import com.awsd.travel.TravelClaimKMRateDB;

public class ViewTravelClaimKMRatesRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-ADMIN")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    ArrayList<TravelClaimKMRate> rates = TravelClaimKMRateDB.getTravelClaimKMRates();
        request.setAttribute("RATES", rates);
        path = "view_km_rates.jsp";
	    return path;
	  }
	}
