package com.esdnl.personnel.recognition.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

import com.esdnl.personnel.recognition.database.*;
import com.esdnl.personnel.recognition.model.bean.*;

public class ListRecognitionCategoryNominationPeriodsRequestHandler  extends RequestHandlerImpl{
	
	public ListRecognitionCategoryNominationPeriodsRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_list_recognition_category_nomination_periods.jsp";
    
    validator = new FormValidator(new FormElement[]{
  			new RequiredFormElement("id")
      });
    
    if(validate_form())
    {
      try
      {
      	RecognitionCategoryBean cat = RecognitionCategoryManager.getRecognitionCategoryBean(form.getInt("id"));
        NominationPeriodBean[] nps = NominationPeriodManager.getNominationPeriodBeans(cat);
        
        request.setAttribute("CATEGORYBEAN", cat);
        request.setAttribute("NOMINATIONPERIODS", nps);
      }
      catch(RecognitionException e)
      {
        e.printStackTrace(System.err);
          
        request.setAttribute("FORM", form);
        request.setAttribute("msg", "Could retrieve NOMINATION PERIODS.");
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
