package com.esdnl.personnel.jobs.handler;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.*;
import com.esdnl.personnel.jobs.bean.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ClearTeacherRecommendationGSURequstHandler implements RequestHandler {
	
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
  	throws ServletException, IOException
	{
	  String path = null;
	  HttpSession session = null;
	  User usr = null;
	  
	  session = request.getSession(false);
	  if((session != null) && (session.getAttribute("usr") != null))
	  {
	    usr = (User) session.getAttribute("usr");
	    if(!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")
	      || usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")))
	    {
	      throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	    }
	  }
	  else
	  {
	    throw new SecurityException("User login required.");
	  }
	  
	  try
	  {
	    JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	    HashMap all_jobs_gsu = (HashMap) session.getAttribute("ALL_JOBS_GSU_BEANS");
	    
	    if(all_jobs_gsu == null){
	    	all_jobs_gsu = new HashMap();
	    }
	    
	    all_jobs_gsu.put(job.getCompetitionNumber(), null);
	    
	    session.setAttribute("ALL_JOBS_GSU_BEANS", all_jobs_gsu);
	    
	    
	    //generate XML for candidate details.
	    String xml = null;
	    StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
	    sb.append("<GSU-BEANS>");
	    sb.append("</GSU-BEANS>");
	    xml = sb.toString().replaceAll("&", "&amp;");
	    
	    System.out.println(xml);
	    
	    PrintWriter out = response.getWriter();
	    
	    response.setContentType("text/xml"); 
	    response.setHeader("Cache-Control", "no-cache");
	    out.write(xml);
	    out.flush();
	    out.close();
	    path = null;
	            
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    request.setAttribute("msg", e.getMessage());
	    path = null;
	  }
	  
	  return path;
	}
}