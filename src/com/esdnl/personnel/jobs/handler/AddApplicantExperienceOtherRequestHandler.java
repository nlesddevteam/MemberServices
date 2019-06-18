package com.esdnl.personnel.jobs.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.personnel.jobs.constants.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddApplicantExperienceOtherRequestHandler implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    SimpleDateFormat sdf = null;
    ApplicantProfileBean profile = null;
    
    
    try
    {
      String from = request.getParameter("from_date");
      String to = request.getParameter("to_date");
      String school_board = request.getParameter("school_board");
      String grds_subs = request.getParameter("grds_subs");
      
      profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
      
      if(profile == null)
      {
        path = "applicant_registration_step_1.jsp";
      }
      else if(StringUtils.isEmpty(from))
      {
        request.setAttribute("errmsg", "Please specify from date.");
        path = "applicant_registration_step_3.jsp";
      }
      else if(StringUtils.isEmpty(to))
      {
        request.setAttribute("errmsg", "Please specify to date.");
        path = "applicant_registration_step_3.jsp";
      }
      else if(StringUtils.isEmpty(school_board))
      {
        request.setAttribute("errmsg", "Please specify school and board name.");
        path = "applicant_registration_step_3.jsp";
      }
      else if(StringUtils.isEmpty(grds_subs))
      {
        request.setAttribute("errmsg", "Please specify grades and/or subjects.");
        path = "applicant_registration_step_3.jsp";
      }
      else
      {
        sdf = new SimpleDateFormat("MM/yyyy");
        
        ApplicantExperienceOtherBean abean = new ApplicantExperienceOtherBean();
        
        abean.setSIN(profile.getSIN());
        abean.setFrom(sdf.parse(from));
        abean.setTo(sdf.parse(to));
        abean.setSchoolAndBoard(school_board);
        abean.setGradesSubjects(grds_subs);
        
        ApplicantExpOtherManager.addApplicantExperienceOtherBean(abean);
        
        request.setAttribute("msg", "Other board Experience successfully added.");
        path = "applicant_registration_step_3.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("errmsg", "Could not add applicant profile ESD general experience.");
      path = "applicant_registration_step_3.jsp";
    }
    catch(ParseException e)
    {
      e.printStackTrace();
      request.setAttribute("errmsg", "Invalid date format.");
      path = "applicant_registration_step_3.jsp";
    }
    

    return path;
  }
}