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

public class AddRecognitionCategoriesRequestHandler  extends RequestHandlerImpl{
	
	public AddRecognitionCategoriesRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_add_recognition_category.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD")){
    	
    	validator = new FormValidator(new FormElement[]{
    			new RequiredFormElement("cat_name"),
    			new RequiredFormElement("cat_desc")
        });
    	
	    if(validate_form()){
	      try{
	      	RecognitionCategoryBean abean = new RecognitionCategoryBean();
	      	abean.setName(form.get("cat_name"));
	      	abean.setDescription(StringUtils.encodeHTML(form.get("cat_desc")));
	      	
	        RecognitionCategoryManager.addRecognitionCategoryBean(abean);
	        
	        request.setAttribute("msg", "Category added successfully.");
	      }
	      catch(RecognitionException e){
	        e.printStackTrace(System.err);
	          
	        request.setAttribute("FORM", form);
	        request.setAttribute("msg", "Could not add recognition category.");
	      }
	    }
	    else{
	      request.setAttribute("FORM", form);
	      request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
	    }
    }
    
    return path;
  }
}
