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

public class EditAgencyContractRequestHandler  extends RequestHandlerImpl
{
  public EditAgencyContractRequestHandler()
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
    
    path = "add_agency_contract.jsp";
    
    
      if(validate_form())
      {
        try
        {
          AgencyContractBean contract = AgencyContractManager.getAgencyContractBean(Integer.parseInt(form.get("id")));
        
          request.setAttribute("AGENCYCONTRACTBEAN", contract);
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not find agency data.");
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