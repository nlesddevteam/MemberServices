package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ChangeSupervisorAjaxRequestHandler extends RequestHandlerImpl {
	public ChangeSupervisorAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
	    HttpSession session = null;
	    User usr = null;
	    TravelClaim claim = null;
	    String op = "";
	    boolean check = false;
	    Personnel supervisor = null;    
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
	    claim = TravelClaimDB.getClaim(Integer.parseInt(request.getParameter("claim_id")));
	    op = request.getParameter("op");
        String msg ="";
        String status="";
	    if(op != null){
	      if(op.equalsIgnoreCase("CONFIRM")){
	          try{
	              supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
	          }catch(NumberFormatException e){
	            supervisor = null;
            	msg="Error Setting Supervisor";
            	status="ERROR";
	          }
	          //all clear
	          check = TravelClaimDB.setSupervisor(claim, supervisor);
	          if(check){
	            	msg="Travel Claim Supervisor Updated";
	            	status="SUCCESS";
	          }else{
		            supervisor = null;
	            	msg="Error Setting Supervisor";
	            	status="ERROR";
	          }
	      }else{
	            supervisor = null;
            	msg="Invlaid Option";
            	status="ERROR";
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
		return null;
	}
}