package com.awsd.personnel.profile.handler;

import com.awsd.personnel.*;
import com.awsd.personnel.profile.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ModifyPersonnelNameRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "", type="";
    String firstname="", lastname="";
    Personnel p = null;
    int i;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-PROFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    
    if(request.getParameter("firstname") != null)
    {
        firstname = request.getParameter("firstname");
    }
    else
    {
      throw new ProfileException("Modify Personnel Name: First Name Require.");
    }
    
    if(request.getParameter("lastname") != null)
    {
        lastname = request.getParameter("lastname");
    }
    else
    {
      throw new ProfileException("Modify Personnel Name: Last Name Require.");
    }

    p = usr.getPersonnel();

    p.setName(firstname, lastname);

    type = request.getRequestURI().substring(i=request.getRequestURI().indexOf("Profile/")+"Profile/".length() , request.getRequestURI().indexOf("/", i));
    
    if(type.equals("Teacher"))
    {
      path = "index.jsp";
      request.setAttribute("msgOK", "SUCCESS! Name change successfully updated.");
    }
    
    return path;
  }
}