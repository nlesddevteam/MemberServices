package com.esdnl.personnel.jobs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.school.*;

import com.esdnl.util.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.personnel.jobs.constants.*;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddApplicantSubPrefsRequestHandler   implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    School[] schools = null;
    Vector sel = null;
    ApplicantProfileBean profile = null;
    
    
    try
    {
      profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
      
      if(profile == null)
      {
        path = "applicant_registration_step_1.jsp";
      }
      else
      {
        schools = (School[]) SchoolDB.getSchools().toArray(new School[0]);
        
        sel = new Vector();
        
        for(int i=0; i < schools.length; i++)
        {
          if(request.getParameter("s_" + schools[i].getSchoolID()) != null)
            sel.add(schools[i]);
        }
        
        ApplicantSubPrefManager.addApplicantSubPrefs(profile, (School[])sel.toArray(new School[0]));
        
        request.setAttribute("msg", "Substitute preferences successfully submitted.");
        path = "applicant_substitute_preferences.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("errmsg", "Could not add Substitute preferences at this time.");
      path = "applicant_substitute_preferences.jsp";
    }
    

    return path;
  }
}