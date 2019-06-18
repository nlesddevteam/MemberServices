package com.esdnl.personnel.jobs.handler;
import com.awsd.servlet.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantEducationPostSSRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    
	    SimpleDateFormat sdf = null;
	    ApplicantProfileBean profile = null;
	    
	    
	    try
	    {
	      String institution = request.getParameter("institution");
	      String from = request.getParameter("from_date");
	      String to = request.getParameter("to_date");
	      String program = request.getParameter("program");
	      //String major = request.getParameter("major");
	      //String minor = request.getParameter("minor");
	      String degree = request.getParameter("degree");
	      int ctype = Integer.parseInt(request.getParameter("dtype"));
	      String dcert = request.getParameter("dcert");
	      String ddiploma = request.getParameter("ddiploma");
	      profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
	      
	      if(profile == null)
	      {
	        path = "applicant_registration_step_1_ss.jsp";
	      }else{
	        sdf = new SimpleDateFormat("MM/yyyy");
	        
	        ApplicantEducationPostSSBean abean = new ApplicantEducationPostSSBean();
	        
	        abean.setSin(profile.getSIN());
	        abean.setInstitution(institution);
	        abean.setFrom(sdf.parse(from));
	        abean.setTo(sdf.parse(to));
	        abean.setProgram(program);
	        abean.setMajor(-1);
	        abean.setMinor(-1);
	        
	        
	        if(ctype == 1){
	        	abean.setDegree(degree);
	        }else if(ctype == 2){
	        	abean.setDegree(ddiploma);
	        }else if( ctype==3){
	        	abean.setDegree(dcert);
	        }else{
	        	abean.setDegree("-1");
	        }
	        abean.setCtype(ctype);
	          
	        
	        ApplicantEducationPostSSManager.addApplicantEducationPostSSBean(abean);
	        
	        request.setAttribute("msg", "Education successfully added.");
	        path = "applicant_registration_step_5_ss.jsp";
	      }
	      
	    }
	    catch(JobOpportunityException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("errmsg", "Could not add applicant education support staff.");
	      path = "applicant_registration_step_5_ss.jsp";
	    }
	    catch(ParseException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("errmsg", "Invalid date format.");
	      path = "applicant_registration_step_5_ss.jsp";
	    }
	    

	    return path;
	  }
	}
