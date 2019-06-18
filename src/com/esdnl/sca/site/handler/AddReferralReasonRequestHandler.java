package com.esdnl.sca.site.handler;

import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.school.*;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.sca.dao.*;
import com.esdnl.sca.model.bean.*;

public class AddReferralReasonRequestHandler extends RequestHandlerImpl
{
  public AddReferralReasonRequestHandler()
  {
    requiredPermissions = new String[]{"SCA-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_referral_reason_code.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("reason_desc")
      });
      
      if(validate_form())
      {
        try
        {
        
          ReferralReason rr = new ReferralReason();
        
          rr.setDescription(form.get("reason_desc"));
          
          SCAManager.addReferralReason(rr);
          
          request.setAttribute("msg", "Referral reason added successfully.");
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not add referral reason.");
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