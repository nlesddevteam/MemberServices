package com.esdnl.personnel.recognition.site.handler;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.recognition.database.NominationManager;
import com.esdnl.personnel.recognition.database.NominationPeriodManager;
import com.esdnl.personnel.recognition.database.RecognitionCategoryManager;
import com.esdnl.personnel.recognition.model.bean.NominationBean;
import com.esdnl.personnel.recognition.model.bean.NominationPeriodBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionCategoryBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteRecognitionNominationRequestHandler   extends RequestHandlerImpl{
	
	public DeleteRecognitionNominationRequestHandler()
  {
    requiredPermissions = new String[]{"PERSONNEL-RECOGNITION-ADMIN-VIEW"};
  }
  
	
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "admin_list_recognition_category_nomination_period_nominations.jsp";
    
    
    	
    	validator = new FormValidator(new FormElement[]{
    			new RequiredFormElement("np_id"),
    			new RequiredFormElement("id")
        });
    	
	    if(validate_form()){
	      try{
	      	
	      	
	        
	      	NominationBean abean = NominationManager.getNominationBean(form.getInt("id"));
	      	
	      	if(abean != null){
	      		NominationManager.deleteNominationBean(abean);
	      		
	      		File f = null;
	      		if((f = new File(ROOT_DIR +"/recognition/nominations/rationales/"+abean.getRationaleFilename())).exists())
	      			f.delete();
	      			
	      	}
	      	else
	      		request.setAttribute("msg", "Nomination DOES NOT EXIST.");
	        
	      	NominationPeriodBean period = NominationPeriodManager.getNominationPeriodBean(form.getInt("np_id"));
	        NominationBean[] nominations = NominationManager.getNominationBeans(period);
	        
	        request.setAttribute("NOMINATIONPERIODBEAN", period);
	        request.setAttribute("NOMINATIONBEANS", nominations);
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
