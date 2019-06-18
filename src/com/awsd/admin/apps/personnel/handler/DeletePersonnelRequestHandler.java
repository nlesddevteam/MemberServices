package com.awsd.admin.apps.personnel.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class DeletePersonnelRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel tmp = null;
    PersonnelCategory cat = null;
    int pid, catid;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getUsername() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("pid") == null)
    {
      throw new AdminException("Personnel ID Required.");
    }
    else
    {
      pid = Integer.parseInt(request.getParameter("pid"));
    }

    PersonnelDB.deletePersonnel(pid);
    session.setAttribute("ADMIN-PERSONNEL_VIEW", null);
    request.setAttribute("page", request.getParameter("page"));
    path = "personnel_admin_view.jsp";
    
    return path;
  }
}