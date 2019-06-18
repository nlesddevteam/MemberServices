package com.esdnl.nicep.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;

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

public class AddStudentGuardianDemographicsRequestHandler  extends RequestHandlerImpl
{
  public AddStudentGuardianDemographicsRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_student_guardian.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id"),
        new RequiredFormElement("firstname"),
        new RequiredFormElement("lastname"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode"),
        new RequiredFormElement("phone1"),
        new RequiredPatternFormElement("phone1", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("phone2", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("email", FormElementPattern.EMAIL_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
          StudentGuardianDemographicsBean g = new StudentGuardianDemographicsBean();
        
          g.setStudentId(Integer.parseInt(form.get("id")));
          g.setFirstname(form.get("firstname"));
          g.setLastname(form.get("lastname"));
          g.setAddress1(form.get("address1"));
          g.setAddress2(form.get("address2"));
          g.setCityTown(form.get("city_town"));
          g.setCountry(form.get("country"));
          g.setStateProvince(form.get("province_state"));
          g.setZipcode(form.get("zipcode"));
          g.setPhone1(form.get("phone1"));
          g.setPhone2(form.get("phone2"));
          g.setEmail(form.get("email"));
            
          g.setGuardianId(StudentGuardianDemographicsManager.addStudentGuardianDemographicsBean(g));
          
          request.setAttribute("STUDENTBEAN", StudentDemographicsManager.getStudentDemographicsBeans(g.getStudentId()));
          
          request.setAttribute("msg", "Parent/Guardian demographics added successfully.");
          
          path = "view_student.jsp";
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
        new RequiredFormElement("firstname"),
        new RequiredFormElement("lastname"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode"),
        new RequiredFormElement("phone1"),
        new RequiredPatternFormElement("phone1", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("phone2", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("email", FormElementPattern.EMAIL_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
          StudentGuardianDemographicsBean g = StudentGuardianDemographicsManager.getStudentGuardianDemographicsBeans(Integer.parseInt(form.get("id")));
        
          g.setFirstname(form.get("firstname"));
          g.setLastname(form.get("lastname"));
          g.setAddress1(form.get("address1"));
          g.setAddress2(form.get("address2"));
          g.setCityTown(form.get("city_town"));
          g.setCountry(form.get("country"));
          g.setStateProvince(form.get("province_state"));
          g.setZipcode(form.get("zipcode"));
          g.setPhone1(form.get("phone1"));
          g.setPhone2(form.get("phone2"));
          g.setEmail(form.get("email"));
          
          StudentGuardianDemographicsManager.updateStudentGuardianDemographicsBean(g);
          
          request.setAttribute("STUDENTBEAN", StudentDemographicsManager.getStudentDemographicsBeans(g.getStudentId()));
          request.setAttribute("msg", "Parent/Guardian demographics updated successfully.");
          
          path = "view_student.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not updated parent/guardian demographics.");
          
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
    else
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id")
      });
      
      if(validate_form())
      {
        try
        {
          request.setAttribute("STUDENTBEAN", StudentDemographicsManager.getStudentDemographicsBeans(Integer.parseInt(form.get("id"))));
        }
        catch(Exception e)
        {
          request.setAttribute("msg", "Add Student Parent/Guardian:<br>" + e.getMessage());
          path="index.jsp";
        }
      }
      else
      {
        request.setAttribute("msg", "Add Student Parent/Guardian:<br>" + StringUtils.encodeHTML(validator.getErrorString()));
        path="index.jsp";
      }
    }
    
    return path;
  }
}