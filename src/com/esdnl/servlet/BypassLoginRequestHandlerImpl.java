package com.esdnl.servlet;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.security.crypto.*;
import com.awsd.servlet.*;

import com.esdnl.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class BypassLoginRequestHandlerImpl 
extends RequestHandlerImpl 
implements LoginNotRequiredRequestHandler
{
  public BypassLoginRequestHandlerImpl()
  {
    super();
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException, IOException
  {
    boolean validated = false;
    
    form = new Form(request);
    
    if(!StringUtils.isEmpty(form.get("u"))&& !StringUtils.isEmpty(form.get("p"))){
      usr = new User(form.get("u"), PasswordEncryption.decrypt(form.get("p")));
      session = request.getSession(true);
      session.setAttribute("usr", usr);
      
      if(requiredPermissions != null)
      {
        for(int i=0; (i < requiredPermissions.length); i++)
        {
          if(usr.getUserPermissions().containsKey(requiredPermissions[i]))
          {
            validated = true;
            break;
              
          }
        }
      }
      else
        validated = true;
    }
    else{
    	session = request.getSession(false);
      if((session != null) && (session.getAttribute("usr") != null))
      {
        usr = (User) session.getAttribute("usr");
        if(requiredPermissions != null)
        {
          for(int i=0; (i < requiredPermissions.length); i++)
          {
            if(usr.getUserPermissions().containsKey(requiredPermissions[i]))
            {
              validated = true;
              break;
              
            }
          }
        }
        else
          validated = true;
      }
    }
    
    if(!validated)
      throw new SecurityException("Illegal Access [" + request.getRequestURI() + "] [" + usr.getLotusUserFullName() + "]");
    
    ROOT_DIR = new File(session.getServletContext().getRealPath("/"));
    
    return null;
  }
}