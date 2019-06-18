package com.awsd.travel.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteTravelClaimAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteTravelClaimAjaxRequestHandler() {

	}
	@Override
	 
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
	    HttpSession session = null;
	    User usr = null;
	    TravelClaim claim = null;
	    String op = "";
	    int id = -1;
	    boolean check = false;
	    boolean isAdmin = false;
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null)){
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))){
		        //send error back
	          	String xml = null;
	        	StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
	        	sb.append("<TRAVELCLAIMS>");
	        	sb.append("<TRAVELCLAIM>");
	        	sb.append("<STATUS>ERROR</STATUS>");
	        	sb.append("<MESSAGE>Illegal Access [" +  usr.getLotusUserFullName() + "]</MESSAGE>");
	        	sb.append("</TRAVELCLAIM>");
	        	sb.append("</TRAVELCLAIMS>");
	        	xml = sb.toString().replaceAll("&", "&amp;");
	        	System.out.println(xml);
	        	PrintWriter out = response.getWriter();
	        	response.setContentType("text/xml");
	        	response.setHeader("Cache-Control", "no-cache");
	        	out.write(xml);
	        	out.flush();
	        	out.close();
	        	throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
        	}
	    }else{
	        //send error back
          	String xml = null;
        	StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
        	sb.append("<TRAVELCLAIMS>");
        	sb.append("<TRAVELCLAIM>");
        	sb.append("<STATUS>ERROR</STATUS>");
        	sb.append("<MESSAGE>User Login Required</MESSAGE>");
        	sb.append("</TRAVELCLAIM>");
        	sb.append("</TRAVELCLAIMS>");
        	xml = sb.toString().replaceAll("&", "&amp;");
        	System.out.println(xml);
        	PrintWriter out = response.getWriter();
        	response.setContentType("text/xml");
        	response.setHeader("Cache-Control", "no-cache");
        	out.write(xml);
        	out.flush();
        	out.close();
        	throw new SecurityException("User login required.");
	    }
	    try{
	      id = Integer.parseInt(request.getParameter("id"));
	    }catch(NumberFormatException e){
	      id = -1;
	    }
	    if(id < 1){
	        //send error back
          	String xml = null;
        	StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
        	sb.append("<TRAVELCLAIMS>");
        	sb.append("<TRAVELCLAIM>");
        	sb.append("<STATUS>ERROR</STATUS>");
        	sb.append("<MESSAGE>CLAIM ID IS REQUIRED FOR DELETE OPERATION</MESSAGE>");
        	sb.append("</TRAVELCLAIM>");
        	sb.append("</TRAVELCLAIMS>");
        	xml = sb.toString().replaceAll("&", "&amp;");
        	System.out.println(xml);
        	PrintWriter out = response.getWriter();
        	response.setContentType("text/xml");
        	response.setHeader("Cache-Control", "no-cache");
        	out.write(xml);
        	out.flush();
        	out.close();
        	throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR DELETE OPERATION.  >>>>>");
	    }else{ 
	    	isAdmin = usr.getUserRoles().containsKey("ADMINISTRATOR");
	    	String msg ="";
	    	String status="";
	    	claim = TravelClaimDB.getClaim(id);
	    	if(!isAdmin && (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()))
	    	{
	    		msg="You do not have permission to delete this travel claim.";
	    		status="ERROR";
	    	}else{      
	        op = request.getParameter("op");
	        if(op != null){
	          if(op.equalsIgnoreCase("CONFIRM")){
	            check = TravelClaimDB.deleteClaim(claim);
	            if(check){
	            	msg="Travel Claim Deleted";
	            	status="SUCCESS";
	            }else{
	            	msg="Travel Claim Not Deleted";
	            	status="ERROR";
	            }
	          }else{
	        	  msg="Invalid operation";
	        	  status="ERROR";
	          }
	        }
	      }
        	String xml = null;
        	StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
        	sb.append("<TRAVELCLAIMS>");
        	sb.append("<TRAVELCLAIM>");
        	sb.append("<STATUS>" + status + "</STATUS>");
        	sb.append("<MESSAGE>" + msg + "</MESSAGE>");
        	sb.append("</TRAVELCLAIM>");
        	sb.append("</TRAVELCLAIMS>");
        	xml = sb.toString().replaceAll("&", "&amp;");
        	System.out.println(xml);
        	PrintWriter out = response.getWriter();
        	response.setContentType("text/xml");
        	response.setHeader("Cache-Control", "no-cache");
        	out.write(xml);
        	out.flush();
        	out.close();
	    }
		return null;
	}
}