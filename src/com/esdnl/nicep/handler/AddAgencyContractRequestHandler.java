package com.esdnl.nicep.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;import com.awsd.security.SecurityException;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.nicep.beans.*;
import com.esdnl.nicep.constants.*;
import com.esdnl.nicep.dao.*;


import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class AddAgencyContractRequestHandler  extends RequestHandlerImpl
{
  public AddAgencyContractRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_agency_contract.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("agency_id"),
        new RequiredFormElement("start_date"),
        new RequiredFormElement("end_date"),
        new RequiredSelectionFormElement("contract_type", -1),
        new RequiredFormElement("contract_value"),
        new RequiredPatternFormElement("start_date", FormElementPattern.DATE_PATTERN),
        new RequiredPatternFormElement("end_date", FormElementPattern.DATE_PATTERN),
        new RequiredPatternFormElement("contract_value", FormElementPattern.CURRENCY_PATTERN)
      });
      
      if(validate_form())
      {        
        try
        {
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(Integer.parseInt(form.get("agency_id")));
          
          AgencyContractBean contract = new AgencyContractBean();
          
          contract.setAgencyId(agency.getAgencyId());
          contract.setContractType(AgencyContractType.get(Integer.parseInt(form.get("contract_type"))));
          contract.setContractTypeValue(Double.parseDouble(form.get("contract_value")));
          contract.setEffectiveDate((new SimpleDateFormat("dd/MM/yyyy")).parse(form.get("start_date")));
          contract.setEndDate((new SimpleDateFormat("dd/MM/yyyy")).parse(form.get("end_date")));
          
          if(form.uploadFileExists("contract_document"))
            contract.setFilename(save_file("contract_document", "/Nisep/agencies/contracts/"));
          
          AgencyContractManager.addAgencyContractBean(contract);
          
          request.setAttribute("AGENCYBEAN", agency);
          
          path = "view_agency.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contract.");
          request.setAttribute("FORM", form);
        }
        catch(ParseException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contract.");
          request.setAttribute("FORM", form);
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contract.");
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
    else if(StringUtils.isEqual(form.get("op"), "UPDATE"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("contract_id"),
        new RequiredFormElement("start_date"),
        new RequiredFormElement("end_date"),
        new RequiredSelectionFormElement("contract_type", -1),
        new RequiredFormElement("contract_value"),
        new RequiredPatternFormElement("start_date", FormElementPattern.DATE_PATTERN),
        new RequiredPatternFormElement("end_date", FormElementPattern.DATE_PATTERN),
        new RequiredPatternFormElement("contract_value", FormElementPattern.CURRENCY_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
          AgencyContractBean contract = AgencyContractManager.getAgencyContractBean(Integer.parseInt(form.get("contract_id")));
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(contract.getAgencyId());
          
          contract.setContractType(AgencyContractType.get(Integer.parseInt(form.get("contract_type"))));
          contract.setContractTypeValue(Double.parseDouble(form.get("contract_value")));
          contract.setEffectiveDate((new SimpleDateFormat("dd/MM/yyyy")).parse(form.get("start_date")));
          contract.setEndDate((new SimpleDateFormat("dd/MM/yyyy")).parse(form.get("end_date")));
          
          if(form.uploadFileExists("contract_document"))
          {
            String old_file = contract.getFilename();
            
            contract.setFilename(save_file("contract_document", "/Nisep/agencies/contracts/"));
            
            if(!StringUtils.isEmpty(old_file))
              new File(ROOT_DIR, "Nisep/agencies/contracts/" + old_file).delete();
          }
              
          AgencyContractManager.updateAgencyContractBean(contract);
          
          request.setAttribute("AGENCYBEAN", agency);
          
          path = "view_agency.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contact.");
          request.setAttribute("FORM", form);
        }
        catch(ParseException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contract.");
          request.setAttribute("FORM", form);
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add agency contract.");
          request.setAttribute("FORM", form);
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        try
        {
          AgencyContactBean contract = AgencyContactManager.getAgencyContactBean(Integer.parseInt(form.get("contract_id")));
          AgencyDemographicsBean agency = AgencyDemographicsManager.getAgencyDemographicsBeans(contract.getAgencyId());
          request.setAttribute("AGENCYBEAN", agency);
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
          request.setAttribute("msg", "Could not retrieve agency.");
          path = "index.jsp";
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
        path = "index.jsp";
      }
    }
    
    return path;
  }
}