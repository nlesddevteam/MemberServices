package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.colaboration.bean.CollaborationException;
import com.esdnl.colaboration.bean.DiscussionBean;
import com.esdnl.colaboration.bean.DiscussionGroupBean;
import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddFeedbackRequestHandler extends RequestHandlerImpl
{
  public AddFeedbackRequestHandler() {
    requiredPermissions = new String[]{"COLLABORATION-GROUP-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);

      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("id")
      });;
      
      if(validate_form())
      {
      	DiscussionBean bean = null;
      	DiscussionGroupBean group = null;
      	
      	try{
      		bean = CollaborationManager.getDiscussionBean(form.getInt("id"));
      		
      		if(session.getAttribute("DISCUSSION_"+bean.getId()) != null){
      			group = (DiscussionGroupBean)session.getAttribute("DISCUSSION_" + bean.getId());
      			group.setComments(CollaborationManager.getGroupCommentBeans(group));
      		}
      		else{
      			group = new DiscussionGroupBean();
      			group.setDiscussion(bean);
      		}
      		request.setAttribute("DISCUSSIONGROUPBEAN", group);
      		
      		path="add_feedback.jsp";
      	}
      	catch(CollaborationException e){
      		request.setAttribute("FORM", form);
      		request.setAttribute("msg", "Discussion topic could not be added. " + e.getMessage());
      		
      		path="index.html";
      	}      	
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
        
        path = "index.html";
      }
   
    	
    return path;
  }
}