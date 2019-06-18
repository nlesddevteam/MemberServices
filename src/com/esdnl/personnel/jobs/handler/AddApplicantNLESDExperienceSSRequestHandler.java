package com.esdnl.personnel.jobs.handler;
import com.awsd.servlet.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.util.StringUtils;

import java.io.*;
import java.text.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantNLESDExperienceSSRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    ApplicantProfileBean profile = null;
	    try
	    {
	    
	      String employed = request.getParameter("employed");
	      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	      Date sdate = null;
	      //if(!(request.getParameter("sdate")  == "")){
	    	//  sdate =(sdf.parse(request.getParameter("sdate")));
	      //}
          
          //String sstatus = request.getParameter("sstatus");
	      profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
	      
	      if(profile == null)
	      {
	        path = "applicant_registration_step_1_ss.jsp";
	      }
	      else
	      {
	        path = "applicant_registration_step_2_ss.jsp"; // default path
	        
	        ApplicantNLESDExperienceSSBean exp = new ApplicantNLESDExperienceSSBean();
	        exp.setSin(profile.getSIN());
	        exp.setCurrentlyEmployed(employed);
	        //fields that have been replaced for now
        	//since value is no blank values
	        exp.setSenorityDate(null);
	        exp.setSenorityStatus("");
	        //not used anymore
	        exp.setPosition1("");
	        exp.setPosition1School(-1);
	        exp.setPosition1Hours("");
	        exp.setPosition2("");
	        exp.setPosition2School(-1);
	        exp.setPosition2Hours("");
	        
	       if(request.getParameter("op") == null){
	        	ApplicantNLESDExperienceSSManager.addApplicantNLESDExperienceSS(exp);
	        	request.setAttribute("msg", "profile added successfully.");
	        	path = "applicant_registration_step_3_ss.jsp";
	        }else{
	        	exp.setId(Integer.parseInt(request.getParameter("id")));
	        	ApplicantNLESDExperienceSSManager.updateApplicantNLESDExperienceSS(exp);
	        	request.setAttribute("msg", "profile updated successfully.");
	        	path = "applicant_registration_step_3_ss.jsp";
	        }
	        //now we check to see if they are adding a current position and save that
	       if(request.getParameter("hidadd").equals("ADDNEW")){
	    	   	ApplicantCurrentPositionBean abean = new ApplicantCurrentPositionBean();
	    	   	abean.setSin(profile.getSIN());
	    	   	abean.setSchoolId(Integer.parseInt(request.getParameter("perm_school")));
	    	   	abean.setPositionHeld(Integer.parseInt(request.getParameter("perm_position")));
	    	   	abean.setPositionHours(request.getParameter("position_hours"));
	    	   	abean.setPositionType(request.getParameter("positiontype"));
	    	   	String startdate=request.getParameter("sdate");
	    	   	if(StringUtils.isEmpty(startdate)){
		        	abean.setStartDate(null);
		        }else{
		        	abean.setStartDate(sdf.parse(startdate));
		        }
	    	   	String enddate=request.getParameter("sdate");
	    	   	if(StringUtils.isEmpty(enddate)){
		        	abean.setEndDate(null);
		        }else{
		        	abean.setEndDate(sdf.parse(enddate));
		        }
	    	   	ApplicantCurrentPositionManager.addApplicantCurrentPositionBean(abean);
	    	   	request.setAttribute("msg", "profile updated successfully.");
	    	   	path = "applicant_registration_step_2_ss.jsp";
	       }
	       
	        request.getSession(true).setAttribute("APPLICANT", profile);
	        request.setAttribute("unioncodes", RequestToHireManager.getUnionCodes());
	      }
	    }catch(Exception e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could not add applicant profile nlesd experience.");
	      path = "applicant_registration_step_2_ss.jsp";
	    }
	    

	    return path;
	  }
	}
