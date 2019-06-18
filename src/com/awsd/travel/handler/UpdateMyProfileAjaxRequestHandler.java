package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.personnel.profile.Profile;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;

public class UpdateMyProfileAjaxRequestHandler extends RequestHandlerImpl
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	    HttpSession session = null;
	    User usr = null;
	    Profile profile = null;
		boolean iserror = false;
		String errormessage="";
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null)){
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))){
				iserror=true;
				errormessage="Illegal Access [" + usr.getLotusUserFullName() + "]";
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }else{
				iserror=true;
				errormessage="User login required.";
				throw new SecurityException("User login required.");
	    }
	    profile = new Profile(usr.getPersonnel(), request.getParameter("cur_street_addr"),
                    request.getParameter("cur_community"),
                    request.getParameter("cur_province"),
                    request.getParameter("cur_postal_code"),
                    request.getParameter("home_phone"),
                    request.getParameter("fax"),
                    request.getParameter("cell_phone"),
                    request.getParameter("gender"));
	    if(request.getParameter("op").equals("Added")){
	    	profile = ProfileDB.addProfile(profile);
	    }else{
	    	profile = ProfileDB.updateProfile(profile);
	    }
	    
		if(iserror){
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<PROFILES>");
			sb.append("<PROFILE>");
			sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
			sb.append("</PROFILE>");
			sb.append("</PROFILES>");
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
			sb.append("<PROFILES>");
			sb.append("<PROFILE>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</PROFILE>");
			sb.append("</PROFILES>");
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