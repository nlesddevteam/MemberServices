package com.esdnl.personnel.jobs.handler;
import com.awsd.servlet.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantEducationSecSSRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    ApplicantProfileBean profile = null;
	    try
	    {
	    
	      String educationlevel = request.getParameter("educationlevel");
	      String schoolname = request.getParameter("schoolname");
	      String schoolcity = request.getParameter("schoolcity");
	      String schoolprovince = request.getParameter("state_province");
	      String yearscompleted = "";
	      String graduated = request.getParameter("graduated");
	      profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
	      
	      if(profile == null)
	      {
	        path = "applicant_registration_step_1_ss.jsp";
	      }
	      else
	      {
	        path = "applicant_registration_step_5_ss.jsp"; // default path
	        
	        ApplicantEducationSecSSBean exp = new ApplicantEducationSecSSBean();
	        exp.setSin(profile.getSIN());
	        exp.setEducationLevel(educationlevel);
	        exp.setSchoolName(schoolname);
	        exp.setSchoolCity(schoolcity);
	        exp.setSchoolProvince(schoolprovince);
	        exp.setYearsCompleted(yearscompleted);
	        exp.setGraduated(graduated);
	        if(request.getParameter("op") == null){
	        	ApplicantEducationSecSSManager.addApplicantEducationSecSSBean(exp);
		        request.setAttribute("msg", "Education added successfully.");
	        }else{
	        	exp.setId(Integer.parseInt(request.getParameter("id")));
	        	ApplicantEducationSecSSManager.updateApplicantEducationSecSSBean(exp);
		        request.setAttribute("msg", "Education updated successfully.");
	        }
	        
	        request.getSession(true).setAttribute("APPLICANT", profile);
			path = "applicant_registration_step_6_ss.jsp";
	      }
	    }catch(Exception e)
	    {
	      e.printStackTrace();
	      request.setAttribute("errmsg", "Could not add education experience.");
	      path = "applicant_registration_step_5_ss.jsp";
	    }
	    

	    return path;
	  }
	}