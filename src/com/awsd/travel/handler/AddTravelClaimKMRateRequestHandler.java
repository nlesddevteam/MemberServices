package com.awsd.travel.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.travel.TravelClaimKMRateDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelClaimKMRateRequestHandler extends RequestHandlerImpl
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
	    
	    if(request.getParameter("sdate") != null && request.getParameter("edate") != null ){
	        request.setAttribute("claimrate", TravelClaimKMRateDB.getTravelClaimKMRate(request.getParameter("sdate"), request.getParameter("edate")));
	    	path = "add_km_rate.jsp";
	    }else{
	    	path = "add_km_rate.jsp";
	    }
	    
	    
	    return path;
	  }
	}