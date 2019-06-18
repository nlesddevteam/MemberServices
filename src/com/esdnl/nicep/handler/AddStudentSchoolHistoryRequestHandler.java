package com.esdnl.nicep.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;import com.awsd.security.SecurityException;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.nicep.beans.*;
import com.esdnl.nicep.constants.*;
import com.esdnl.nicep.dao.*;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddStudentSchoolHistoryRequestHandler extends RequestHandlerImpl
{
  public AddStudentSchoolHistoryRequestHandler()
  {
    requiredPermissions = new String[]{"NISEP-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "add_student_school_history.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      
      
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("student_id"),
        new RequiredFormElement("school_id"),
        new RequiredFormElement("school_year"),
        new RequiredFormElement("term"),
      });
      
      if(validate_form())
      {  
        try
        {
          StudentDemographicsBean student = StudentDemographicsManager.getStudentDemographicsBeans(Integer.parseInt(form.get("student_id")));
          
          StudentSchoolHistoryBean history = new StudentSchoolHistoryBean();
          
          history.setStudentId(student.getStudentId());
          history.setSchoolId(Integer.parseInt(form.get("school_id")));
          history.setSchoolYear(form.get("school_year"));
          history.setTerm(Integer.parseInt(form.get("term")));
          
          history.setHistoryId(StudentSchoolHistoryManager.addStudentSchoolHistoryBean(history));
          
          request.setAttribute("STUDENTBEAN", student);
          
          path = "view_student_school_history.jsp";
          
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          request.setAttribute("msg", "Could not add student school.");
          request.setAttribute("FORM", form);
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        try
        {
          request.setAttribute("STUDENTBEAN", StudentDemographicsManager.getStudentDemographicsBeans(Integer.parseInt(form.get("student_id"))));
        }
        catch(NICEPException e)
        {
          path = "index.jsp";
          request.setAttribute("msg", "Could not retrieve student.");
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
          StudentDemographicsBean student = StudentDemographicsManager.getStudentDemographicsBeans(Integer.parseInt(form.get("id")));
          
          request.setAttribute("STUDENTBEAN", student);
        }
        catch(NICEPException e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not add student.");
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