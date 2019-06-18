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
import com.awsd.travel.TravelClaimItemsDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteTravelClaimItemAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteTravelClaimItemAjaxRequestHandler() {

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
	    int cid = -1;
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
	      cid = Integer.parseInt(request.getParameter("clid"));
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
        	sb.append("<MESSAGE>CLAIM ITEM ID IS REQUIRED FOR DELETE OPERATION</MESSAGE>");
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
	      throw new TravelClaimException("<<<<< CLAIM ITEM ID IS REQUIRED FOR DELETE OPERATION.  >>>>>");
	    }else{ 
	    	claim = TravelClaimDB.getClaim(cid);
	    	String msg ="";
	    	String status="";
	    	if((claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()))
	    	{
	    		msg="You do not have permission to delete this travel claim item.";
	    		status="ERROR";
	    	}else{      
	    		op = request.getParameter("op");
	    		if(op != null){
	    			if(op.equalsIgnoreCase("CONFIRM")){
	    				check = TravelClaimItemsDB.deleteClaimItem(id);
	    				if(check){
	    						msg="Travel Claim Item Deleted";
	    						status="SUCCESS";
	    				}else{
	    						msg="Travel Claim Item Not Deleted";
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