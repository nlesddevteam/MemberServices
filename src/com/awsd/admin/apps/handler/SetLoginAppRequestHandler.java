package com.awsd.admin.apps.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SetLoginAppRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    int gid;
    String app;
    PersonnelCategory cat = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    app = (String) request.getParameter("app");
    if(app == null)
    {
      throw new AdminException("Login Application Selection Required.");
    }

    if(request.getParameter("group") == null)
    {
      throw new AdminException("Group Selection Required.");
    }
    else
    {
      gid = Integer.parseInt(request.getParameter("group"));
    }

    cat = PersonnelCategoryDB.getPersonnelCategory(gid);

    if(!app.equalsIgnoreCase("NONE"))
    {
      PersonnelDB.setViewOnNextLogon(cat, app);
    }
    else
    {
      PersonnelDB.setViewOnNextLogon(cat, null);
    }

    request.setAttribute("msg", "Login Application Set Successfully");

    path = "loginApp.jsp";
    
    return path;
  }
}