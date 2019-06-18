package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.colaboration.bean.*;
import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddFeedbackCommentRequestHandler extends RequestHandlerImpl
{
  public AddFeedbackCommentRequestHandler() {
    requiredPermissions = new String[]{"COLLABORATION-GROUP-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);

      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("comment")
      });
      
      if(validate_form())
      {
      	DiscussionBean bean = null;
      	DiscussionGroupBean group = null;
      	
      	try{
      		bean = CollaborationManager.getDiscussionBean(form.getInt("id"));
      		
      		if(!StringUtils.isEmpty(form.get("group_id")))
      			group = CollaborationManager.getDiscussionGroupBean(form.getInt("group_id"));
      		else if(!StringUtils.isEmpty(form.get("group_name"))){
      			group = new DiscussionGroupBean();
        		group.setGroupName(form.get("group_name"));
      		}
      		
      		
      		
      		if(group == null){
      			group = new DiscussionGroupBean();
        		group.setDiscussion(bean);
        		
        		request.setAttribute("msg", "Group Name is required.)");
      		}
      		else{
      			group.setDiscussion(bean);
      			
      			if(group.getId() <= 0){
      				group = CollaborationManager.addDiscussionGroupBean(group);
      				
      				session.setAttribute("DISCUSSION_" + bean.getId(), group);
      			}
      			
      			GroupCommentBean comment = new GroupCommentBean();
      			comment.setGroup(group);
      			comment.setComment(form.get("comment"));
      			
      			GroupCommentBean[] comments = CollaborationManager.addGroupCommentBean(comment);
      			
      			group.setComments(comments);
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