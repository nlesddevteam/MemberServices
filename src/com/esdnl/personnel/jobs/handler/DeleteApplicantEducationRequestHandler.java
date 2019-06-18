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

public class DeleteApplicantEducationRequestHandler   implements LoginNotRequiredRequestHandler
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
        path = "applicant_registration_step_5.jsp";
      }
      else
      {
        ApplicantEducationManager.deleteApplicantEducationBean(Integer.parseInt(id));
        
        request.setAttribute("msg", "Education successfully deleted.");
        path = "applicant_registration_step_5.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("msg", "Could not delete applicant education.");
      path = "applicant_registration_step_5.jsp";
    }
    
    return path;
  }
}