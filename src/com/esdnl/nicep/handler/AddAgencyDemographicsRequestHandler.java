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

public class AddAgencyDemographicsRequestHandler extends RequestHandlerImpl
{
  public AddAgencyDemographicsRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_agency.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("agency_name"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode")
      });
      
      if(validate_form())
      {
        try
        {
          AgencyDemographicsBean agency = new AgencyDemographicsBean();
        
          agency.setAddress1(form.get("address1"));
          agency.setAddress2(form.get("address2"));
          agency.setCityTown(form.get("city_town"));
          agency.setCountry(form.get("country"));
          agency.setName(form.get("agency_name"));
          agency.setProvinceState(form.get("province_state"));
          agency.setZipcode(form.get("zipcode"));
            
          agency.setAgencyId(AgencyDemographicsManager.addAgencyDemographicsBean(agency));
          
          request.setAttribute("AGENCYBEAN", agency);
          
          path = "view_agency.jsp";
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
    else if(StringUtils.isEqual(form.get("op"), "UPDATE"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id"),
        new RequiredFormElement("agency_name"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode")
      });
      
      if(validate_form())
      {
        try
        {
          AgencyDemographicsBean agency = new AgencyDemographicsBean();
        
          agency.setAgencyId(Integer.parseInt(form.get("id")));
          agency.setAddress1(form.get("address1"));
          agency.setAddress2(form.get("address2"));
          agency.setCityTown(form.get("city_town"));
          agency.setCountry(form.get("country"));
          agency.setName(form.get("agency_name"));
          agency.setProvinceState(form.get("province_state"));
          agency.setZipcode(form.get("zipcode"));
          
          AgencyDemographicsManager.updateAgencyDemographicsBean(agency);
          
          request.setAttribute("AGENCYBEAN", agency);
          request.setAttribute("msg", "Agency demographics updated successfully.");
          
          path = "view_agency.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not updated agency.");
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