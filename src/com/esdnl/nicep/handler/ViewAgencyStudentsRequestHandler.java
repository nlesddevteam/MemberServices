package com.esdnl.nicep.handler;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.nicep.beans.*;
import com.esdnl.nicep.dao.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewAgencyStudentsRequestHandler extends RequestHandlerImpl
{
  public ViewAgencyStudentsRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
    
    validator = new FormValidator(new FormElement[]{
      new RequiredFormElement("id")
    });
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "view_agency_students.jsp";
    
    if(validate_form())
    {
      try
      {
        AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(Integer.parseInt(form.get("id")));
        
        request.setAttribute("AGENCYBEAN", agency);
        
        StudentDemographicsBean[] students = StudentDemographicsManager.getStudentDemographicsBeans(agency);
        
        request.setAttribute("STUDENTBEANS", students);
      }
      catch(NICEPException e)
      {
        e.printStackTrace(System.err);
          
        request.setAttribute("FORM", form);
        request.setAttribute("msg", "Could retrieve agency students.");
      }
    }
    else
    {
      request.setAttribute("FORM", form);
      request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
    }
    
    return path;
  }
}