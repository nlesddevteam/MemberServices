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

public class AddStudentDemographicsRequestHandler extends RequestHandlerImpl
{
  public AddStudentDemographicsRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_student.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("firstname"),
        new RequiredFormElement("lastname"),
        new RequiredFormElement("dob"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode"),
        new RequiredFormElement("phone1"),
        new RequiredPatternFormElement("dob", FormElementPattern.DATE_PATTERN),
        //new RequiredPatternFormElement("phone1", FormElementPattern.PHONE_PATTERN), -- international numbers
        //new RequiredPatternFormElement("phone2", FormElementPattern.PHONE_PATTERN), -- international numbers
        new RequiredPatternFormElement("email", FormElementPattern.EMAIL_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
          StudentDemographicsBean student = new StudentDemographicsBean();
        
          student.setFirstname(form.get("firstname"));
          student.setLastname(form.get("lastname"));
          student.setDateOfBirth(new SimpleDateFormat(StudentDemographicsBean.DATE_FORMAT).parse(form.get("dob")));
          student.setAddress1(form.get("address1"));
          student.setAddress2(form.get("address2"));
          student.setCityTown(form.get("city_town"));
          student.setCountry(form.get("country"));
          student.setStateProvince(form.get("province_state"));
          student.setZipcode(form.get("zipcode"));
          student.setPhone1(form.get("phone1"));
          student.setPhone2(form.get("phone2"));
          student.setEmail(form.get("email"));
            
          student.setStudentId(StudentDemographicsManager.addStudentDemographicsBean(student));
          
          request.setAttribute("STUDENTBEAN", student);
          
          path = "view_student.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not add agency.");
        }
        catch(ParseException e)
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
        new RequiredFormElement("dob"),
        new RequiredFormElement("address1"),
        new RequiredFormElement("city_town"),
        new RequiredFormElement("province_state"),
        new RequiredFormElement("country"),
        new RequiredFormElement("zipcode"),
        new RequiredFormElement("phone1"),
        new RequiredPatternFormElement("dob", FormElementPattern.DATE_PATTERN),
        new RequiredPatternFormElement("phone1", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("phone2", FormElementPattern.PHONE_PATTERN),
        new RequiredPatternFormElement("email", FormElementPattern.EMAIL_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
          StudentDemographicsBean student = StudentDemographicsManager.getStudentDemographicsBeans(Integer.parseInt(form.get("id")));
        
          student.setFirstname(form.get("firstname"));
          student.setLastname(form.get("lastname"));
          student.setDateOfBirth(new SimpleDateFormat(StudentDemographicsBean.DATE_FORMAT).parse(form.get("dob")));
          student.setAddress1(form.get("address1"));
          student.setAddress2(form.get("address2"));
          student.setCityTown(form.get("city_town"));
          student.setCountry(form.get("country"));
          student.setStateProvince(form.get("province_state"));
          student.setZipcode(form.get("zipcode"));
          student.setPhone1(form.get("phone1"));
          student.setPhone2(form.get("phone2"));
          student.setEmail(form.get("email"));
          
          StudentDemographicsManager.updateStudentDemographicsBean(student);
          
          request.setAttribute("STUDENTBEAN", student);
          request.setAttribute("msg", "Student demographics updated successfully.");
          
          path = "view_student.jsp";
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not updated student.");
        }
        catch(ParseException e)
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