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

public class AddAgencyStudentRequestHandler extends RequestHandlerImpl
{
  public AddAgencyStudentRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_agency_student.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      
      
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id"),
        new RequiredFormElement("name"),
        
      });
      
      if(validate_form())
      {  
        try
        {
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(Integer.parseInt(form.get("agency_id")));
          
          AgencyContactBean contact = new AgencyContactBean();
          
          contact.setAgencyId(agency.getAgencyId());
          contact.setEmail(form.get("email"));
          contact.setName(form.get("name"));
          contact.setPhone1(form.get("phone1"));
          contact.setPhone2(form.get("phone2"));
          contact.setPhone3(form.get("phone3"));
          
          AgencyContactManager.addAgencyContactBean(contact);
          
          request.setAttribute("AGENCYBEAN", agency);
          
          path = "view_agency.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contact.");
          request.setAttribute("FORM", form);
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        try
        {
          request.setAttribute("AGENCYBEAN", AgencyDemographicsManager.getAgencyDemographicsBeans(Integer.parseInt(form.get("agency_id"))));
        }
        catch(NICEPException e)
        {
          path = "index.jsp";
          request.setAttribute("msg", "Could not retrieve agency.");
        }
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    }
    else
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id"),
      });
      
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
    }
    
    return path;
  }
}