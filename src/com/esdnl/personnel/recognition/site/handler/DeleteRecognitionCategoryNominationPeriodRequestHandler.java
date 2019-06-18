package com.esdnl.personnel.recognition.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.recognition.database.NominationPeriodManager;
import com.esdnl.personnel.recognition.database.RecognitionCategoryManager;
import com.esdnl.personnel.recognition.model.bean.NominationPeriodBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionCategoryBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.StringUtils;

public class DeleteRecognitionCategoryNominationPeriodRequestHandler  extends RequestHandlerImpl{
	
	public DeleteRecognitionCategoryNominationPeriodRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
	
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_list_recognition_category_nomination_periods.jsp";
    
    
    	
    	validator = new FormValidator(new FormElement[]{
    			new RequiredFormElement("cat_id"),
    			new RequiredFormElement("id")
        });
    	
	    if(validate_form()){
	      try{
	      	
	      	RecognitionCategoryBean cat = RecognitionCategoryManager.getRecognitionCategoryBean(form.getInt("cat_id"));
	      	NominationPeriodBean abean = NominationPeriodManager.getNominationPeriodBean(form.getInt("id"));
	      	
	      	if(abean != null){
	      		NominationPeriodManager.delNominationPeriodBean(abean);
	      	}
	      	else
	      		request.setAttribute("msg", "Nomination Period DOES NOT EXIST.");
	        
	        request.setAttribute("CATEGORYBEAN", cat);
	        request.setAttribute("NOMINATIONPERIODS", NominationPeriodManager.getNominationPeriodBeans(cat));
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
    
    return path;
  }
}
