package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class SetSchoolRequestHandler  extends RequestHandlerImpl{
	
	public SetSchoolRequestHandler()
  {
    requiredPermissions = new String[]{"MAINTENANCE-SCHOOL-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "index.jsp";
    
    validator = new FormValidator(new FormElement[]{
  			new RequiredFormElement("id")
      });
    
    if(validate_form())
    {
      try
      {
      	if(form.getInt("id") == -1)
          usr.getPersonnel().setSchool(null);
        else
        	usr.getPersonnel().setSchool(SchoolDB.getSchool(form.getInt("id")));
      }
      catch(SchoolException e)
      {
        e.printStackTrace(System.err);
          
        request.setAttribute("FORM", form);
        request.setAttribute("msg", "Could set school id.");
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
