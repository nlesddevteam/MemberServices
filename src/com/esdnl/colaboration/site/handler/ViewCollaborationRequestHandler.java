package com.esdnl.colaboration.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.esdnl.servlet.*;
import com.esdnl.colaboration.dao.*;
import com.esdnl.colaboration.bean.*;


public class ViewCollaborationRequestHandler extends RequestHandlerImpl
{
  public ViewCollaborationRequestHandler() {
    requiredPermissions = new String[]{"COLLABORATION-ADMIN-VIEW","COLLABORATION-GROUP-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    try{
  		request.setAttribute("DISCUSSIONBEANS", CollaborationManager.getTodaysDiscussionBeans());
  	}
  	catch(CollaborationException e){
  		e.printStackTrace();
  	}
  	
    if(usr.getUserPermissions().containsKey("COLLABORATION-ADMIN-VIEW"))	
    	path = "admin_index.jsp";
    else if(usr.getUserPermissions().containsKey("COLLABORATION-GROUP-VIEW"))
    	path = "group_index.jsp";
    	
    return path;
  }
}