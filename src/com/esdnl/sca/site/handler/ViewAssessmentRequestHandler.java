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

public class ViewAssessmentRequestHandler extends RequestHandlerImpl
{
  public ViewAssessmentRequestHandler()
  {
    requiredPermissions = new String[]{"SCA-ADMIN-VIEW","SCA-PRINCIPAL-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "view_assessment.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "UPDATE"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id"),
        new RequiredSelectionFormElement("assessment_status", -1)
      });
      
      if(validate_form())
      {
        try
        {
          
          Assessment a = SCAManager.getAssessmentBean(form.getInt("id"));
          
          a.setStatus(AssessmentStatus.get(form.getInt("assessment_status")));
          
          SCAManager.updateAssessment(a);
          
          request.setAttribute("ASSESSMENTBEAN", a);
          request.setAttribute("msg", "Updated assessment to waitlist successfully.");
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not update assessment.");
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    }
    else
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id")
      });
      
      if(validate_form())
      {
        try
        {
          request.setAttribute("ASSESSMENTBEAN", SCAManager.getAssessmentBean(form.getInt("id")));  
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not update assessment.");
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