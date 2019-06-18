package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.pd.PDException;
import com.awsd.personnel.pd.PersonnelPD;
import com.awsd.personnel.pd.PersonnelPDDB;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddNewClaimAjaxRequestHandler extends RequestHandlerImpl {
	public AddNewClaimAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
        String claim_type = form.get("ctype");
        Personnel supervisor = null;
        supervisor = usr.getPersonnel().getSupervisor();
        if(supervisor == null){
		    try{
		        supervisor = PersonnelDB.getPersonnel(Integer.parseInt(form.get("supervisior")));
		    }
		    catch(NumberFormatException e){
		      supervisor = null;
		    }
        }
        if(supervisor == null){
      	  	//send xml back with error
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>NO SUPERVISOR</MESSAGE>");
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
        	if((claim_type != null)&&(claim_type.equals("o"))){ //this is a regular monthly claim.
        		int claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, form.get("year"), Integer.parseInt(form.get("month")));
    			String xml = null;
    			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
    			sb.append("<TRAVELCLAIMS>");
    			sb.append("<TRAVELCLAIM>");
    			sb.append("<MESSAGE>ADDED</MESSAGE>");
    			sb.append("<CLAIMID>" + claim_id + "</CLAIMID>");
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                PersonnelPD pd = null;
                String year="";
                int month=-1;
                try{
                	pd = new PersonnelPD(form.get("title"), form.get("desc"), sdf.parse(form.get("sdate")), sdf.parse(form.get("fdate")));
                	Calendar cal = Calendar.getInstance();
                	cal.setTime(pd.getStartDate());
                	year = Utils.getSchoolYear(cal);
                	month = cal.get(Calendar.MONTH);
                	pd = PersonnelPDDB.addPD(usr.getPersonnel(), pd);
                }
                catch(PDException e){
                  pd.setID(-1);
                } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                if(pd.getID() > 0){  
                	int claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, year, month, pd);
                	String xml = null;
                	StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
                	sb.append("<TRAVELCLAIMS>");
                	sb.append("<TRAVELCLAIM>");
                	sb.append("<MESSAGE>ADDED</MESSAGE>");
                	sb.append("<CLAIMID>" + claim_id + "</CLAIMID>");
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
        	}
        }
        return null;
	}
}