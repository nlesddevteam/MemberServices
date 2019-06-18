package com.esdnl.survey.site.handler;

import com.esdnl.util.*;
import com.esdnl.survey.bean.*;
import com.esdnl.survey.dao.*;
import com.esdnl.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewSurveySectionsRequestHandler extends RequestHandlerImpl {
	
	public ViewSurveySectionsRequestHandler(){
		requiredPermissions = new String[]{"SURVEY-ADMIN-VIEW"};
		
		validator = new FormValidator(new FormElement[]{
  			new RequiredFormElement("id")
      });
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
	{
		super.handleRequest(request, response);
	  
	  try
	  {
	  	if(validate_form()){
				
	  		SurveyBean survey = SurveyManager.getSuveryBean(form.getInt("id"));
	  		
	  		if(survey != null){
	  			request.setAttribute("SURVEY_BEAN", survey);
	  			request.setAttribute("SURVEY_SECTION_BEANS", SurveySectionManager.getSuverySectionBeans(survey));
	  			
	  			path = "survey_sections.jsp";
	  		}
	  		else{
	  			request.setAttribute("msg", "Survey with id=" + form.getInt("id") + "could not be found.");
	  			
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
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    request.setAttribute("msg", e.getMessage());
	    
	    path = "index.jsp";
	  }
	  
	  return path;
	}
}

