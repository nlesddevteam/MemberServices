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

public class DeleteAgencyContactRequestHandler extends RequestHandlerImpl
{
  public DeleteAgencyContactRequestHandler()
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
          AgencyContactBean contact = AgencyContactManager.getAgencyContactBean(Integer.parseInt(form.get("id")));
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(contact.getAgencyId());
          AgencyContactManager.deleteAgencyContactBean(contact.getContactId());
          
          request.setAttribute("AGENCYBEAN", agency);
          request.setAttribute("msg", "Agency contact deleted successfully.");
        }
        catch(NICEPException e)
        {
          path = "index.jsp";
          
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not find agency data.");
        }
      }
      else
      {
        try
        {
          AgencyContactBean contact = AgencyContactManager.getAgencyContactBean(Integer.parseInt(form.get("id")));
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(contact.getAgencyId());
          
          request.setAttribute("AGENCYBEAN", agency);
        }
        catch(NICEPException e)
        {
          path = "index.jsp";
        }
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    
    return path;
  }
}