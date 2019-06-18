package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.User;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;

public class SaveTravelClaimChangesAjaxRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    TravelClaim claim = null;
	    TravelClaimItem item = null;
	    Iterator iter = null;
	    int id = -1;
	    boolean done = false;
		String errormessage="";
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null)){
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))){
				errormessage="Illegal Access [" + usr.getLotusUserFullName() + "]";
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }else{
			errormessage="User login required.";
			throw new SecurityException("User login required.");
	    }
	    try{
	      id = Integer.parseInt(request.getParameter("id"));
	    }catch(NumberFormatException e){
	      id = -1;
	    }
	    if(id > 0){
	      claim = TravelClaimDB.getClaim(id);
	      if(claim != null){
	        request.setAttribute("TRAVELCLAIM", claim);
	        if(request.getParameter("op") != null){
	        	if(request.getParameter("op").equalsIgnoreCase("CONFIRM")){
	        		iter = claim.getItems().iterator();
			        	while(iter.hasNext()){
			        		item = (TravelClaimItem) iter.next();
			            		if(request.getParameter("item_" + item.getItemID()) != null){
			            			try{
			            					item.setItemOther(Double.parseDouble(request.getParameter("item_" + item.getItemID()).replaceAll("[$,]","")));
			            					done = TravelClaimItemsDB.updateClaimItem(claim, usr.getPersonnel(), item);
			            			}catch(Exception e){
			            				e.printStackTrace(System.err);
			            				done = true;
			            			}
			            		}
			            }
			            if(!done){
			            	errormessage="Some changes could not be saved...";
			            }else{
			            	errormessage="SUCCESS";
			            }
	        	}else{
		        	errormessage="Invalid option";
	        	}
	        }else{
	        	errormessage="Invalid option";
	        }
	      }else{
	        	errormessage="Could not find Travel Claim";
	      }
	    }else{
        	errormessage="Invalid Travel Claim ID";
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<TRAVELCLAIMS>");
		sb.append("<TRAVELCLAIM>");
		sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
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