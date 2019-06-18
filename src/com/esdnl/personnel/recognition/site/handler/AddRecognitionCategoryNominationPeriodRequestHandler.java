package com.esdnl.personnel.recognition.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.*;

import com.esdnl.util.StringUtils;

import com.esdnl.personnel.recognition.database.*;
import com.esdnl.personnel.recognition.model.bean.*;

public class AddRecognitionCategoryNominationPeriodRequestHandler  extends RequestHandlerImpl{
	
	public AddRecognitionCategoryNominationPeriodRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
	
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_list_recognition_category_nomination_periods.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD")){
    	
    	validator = new FormValidator(new FormElement[]{
    			new RequiredFormElement("id"),
    			new RequiredPatternFormElement("start_date", FormElementPattern.DATE_PATTERN),
    			new RequiredPatternFormElement("end_date", FormElementPattern.DATE_PATTERN)
        });
    	
	    if(validate_form()){
	      try{
	      	
	      	RecognitionCategoryBean abean = RecognitionCategoryManager.getRecognitionCategoryBean(form.getInt("id"));
	      	
	      	if(!form.getDate("start_date").after(form.getDate("end_date"))){
		      	NominationPeriodBean pbean = new NominationPeriodBean();
		      	pbean.setCategory(abean);
		      	pbean.setStartDate(form.getDate("start_date"));
		      	pbean.setEndDate(form.getDate("end_date"));
		      	
		        int check = NominationPeriodManager.addNominationPeriodBean(pbean);
		        
		        if(check == 0)
		        	request.setAttribute("msg", "Nomination Period added successfully.");
		        else
		        	request.setAttribute("msg", "Cannot add overlapping nomination periods.");
	      	}
	      	else
	      		request.setAttribute("msg", "Start Date MUST be before End Date.");
	        
	        request.setAttribute("CATEGORYBEAN", abean);
	        request.setAttribute("NOMINATIONPERIODS", NominationPeriodManager.getNominationPeriodBeans(abean));
	      }
	      catch(RecognitionException e){
	        e.printStackTrace();
	          
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
