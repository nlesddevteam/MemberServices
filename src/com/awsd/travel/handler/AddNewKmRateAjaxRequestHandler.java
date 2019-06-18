package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.travel.TravelClaimKMRate;
import com.awsd.travel.TravelClaimKMRateDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddNewKmRateAjaxRequestHandler extends RequestHandlerImpl {
	public AddNewKmRateAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
        TravelClaimKMRate trate = new TravelClaimKMRate();
        trate.setEffectiveStartDate(form.getDate("estartdate"));
        trate.setBaseRate(form.getDouble("basekmrate"));
        trate.setEffectiveEndDate(form.getDate("eenddate"));
        trate.setApprovedRate(form.getDouble("approvedkmrate"));
        boolean result=false;
        
        if(form.get("op").equals("ADD")){
        	result = TravelClaimKMRateDB.addClaimKmRate(trate);
        }else{
        	result = TravelClaimKMRateDB.updateClaimKmRate(trate);
        }
        	
        if(result){
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
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
        }else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>ERROR ADDING KM RATE</MESSAGE>");
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
        }   return null;
	}
}