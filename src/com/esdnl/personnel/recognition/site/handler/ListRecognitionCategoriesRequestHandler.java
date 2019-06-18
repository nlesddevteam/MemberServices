package com.esdnl.personnel.recognition.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

import com.esdnl.personnel.recognition.database.*;
import com.esdnl.personnel.recognition.model.bean.*;

public class ListRecognitionCategoriesRequestHandler  extends RequestHandlerImpl{
	
	public ListRecognitionCategoriesRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_list_recognition_categories.jsp";
    
    if(validate_form())
    {
      try
      {
        RecognitionCategoryBean[] cats = RecognitionCategoryManager.getRecognitionCategoryBeans();
        
        request.setAttribute("CATEGORIES", cats);
      }
      catch(RecognitionException e)
      {
        e.printStackTrace(System.err);
          
        request.setAttribute("FORM", form);
        request.setAttribute("msg", "Could retrieve agency students.");
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
