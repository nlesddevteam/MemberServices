package com.esdnl.personnel.jobs.handler;

import com.awsd.servlet.*;
import com.esdnl.util.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddApplicantEducationRequestHandler  implements LoginNotRequiredRequestHandler
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
      String major = request.getParameter("major");
      String major_courses = request.getParameter("major_courses");
      String minor = request.getParameter("minor");
      String minor_courses = request.getParameter("minor_courses");
      String degree = request.getParameter("degree");
      
      profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
      
      if(profile == null)
      {
        path = "applicant_registration_step_1.jsp";
      }
      else if(StringUtils.isEmpty(institution))
      {
        request.setAttribute("errmsg", "Please specify name of institution.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(from))
      {
        request.setAttribute("errmsg", "Please specify from date.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(to))
      {
        request.setAttribute("errmsg", "Please specify to date.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(program))
      {
        request.setAttribute("errmsg", "Please specify program/faculty.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(major))
      {
        request.setAttribute("errmsg", "Please specify major.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(major_courses))
      {
        request.setAttribute("errmsg", "Please specify number major courses.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(minor))
      {
        request.setAttribute("errmsg", "Please specify minor.");
        path = "applicant_registration_step_5.jsp";
      }
      else if(StringUtils.isEmpty(minor_courses))
      {
        request.setAttribute("errmsg", "Please specify number minor courses.");
        path = "applicant_registration_step_5.jsp";
      }
      
      else
      {
        sdf = new SimpleDateFormat("MM/yyyy");
        
        ApplicantEducationBean abean = new ApplicantEducationBean();
        
        abean.setSIN(profile.getSIN());
        abean.setInstitutionName(institution);
        abean.setFrom(sdf.parse(from));
        abean.setTo(sdf.parse(to));
        abean.setProgramFacultyName(program);
        abean.setMajor(Integer.parseInt(major));
        abean.setNumberMajorCourses(Integer.parseInt(major_courses));
        abean.setMinor(Integer.parseInt(minor));
        abean.setNumberMinorCourses(Integer.parseInt(minor_courses));
        
        if(!StringUtils.isEmpty(degree) && !degree.equals("0"))
          abean.setDegreeConferred(degree);
        
        ApplicantEducationManager.addApplicantEducationBean(abean);
        
        request.setAttribute("msg", "Education successfully added.");
        path = "applicant_registration_step_5.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("errmsg", "Could not add applicant education.");
      path = "applicant_registration_step_5.jsp";
    }
    catch(ParseException e)
    {
      e.printStackTrace();
      request.setAttribute("errmsg", "Invalid date format.");
      path = "applicant_registration_step_5.jsp";
    }
    

    return path;
  }
}