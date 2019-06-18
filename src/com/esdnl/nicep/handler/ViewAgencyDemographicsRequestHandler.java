package com.esdnl.nicep.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.nicep.beans.*;
import com.esdnl.nicep.constants.*;
import com.esdnl.nicep.dao.*;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewAgencyDemographicsRequestHandler extends RequestHandlerImpl
{
  public ViewAgencyDemographicsRequestHandler()
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
    
    path = "view_agency.jsp";
    
    if(validate_form())
    {
      try
      {
        AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(Integer.parseInt(form.get("id")));
        
        request.setAttribute("AGENCYBEAN", agency);
      }
      catch(NICEPException e)
      {
        e.printStackTrace(System.err);
          
        request.setAttribute("FORM", form);
        request.setAttribute("msg", "Could not add agency.");
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