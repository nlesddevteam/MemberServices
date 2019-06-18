package com.esdnl.personnel.jobs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
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

public class DeleteApplicantExperienceOtherRequestHandler   implements LoginNotRequiredRequestHandler
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
      
      String id = request.getParameter("del");
     
      if(StringUtils.isEmpty(id))
      {
        request.setAttribute("msg", "ID required for deletion.");
        path = "applicant_registration_step_3.jsp";
      }
      else
      {
        ApplicantExpOtherManager.deleteApplicantExperienceOtherBean(Integer.parseInt(id));
        
        request.setAttribute("msg", "Other board experience successfully deleted.");
        path = "applicant_registration_step_3.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("msg", "Could not delete other board experience education.");
      path = "applicant_registration_step_3.jsp";
    }
    
    return path;
  }
}