package com.esdnl.sca.site.handler;

import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.awsd.security.*;import com.awsd.security.SecurityException;import com.awsd.security.SecurityException;
import com.awsd.school.*;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.sca.dao.*;
import com.esdnl.sca.model.bean.*;

public class AddAssessmentTypeRequestHandler  extends RequestHandlerImpl
{
  public AddAssessmentTypeRequestHandler()
  {
    requiredPermissions = new String[]{"SCA-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_assessment_type.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("type_name")
      });
      
      if(validate_form())
      {
        try
        {
        
          AssessmentType at = new AssessmentType();
        
          at.setDescription(form.get("type_name"));
          
          SCAManager.addAssessessmentType(at);
          
          request.setAttribute("msg", "Assessment Type added successfully.");
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not add assessment type.");
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