package com.awsd.admin.apps.personnel.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ChangePersonnelCategoryRequestHandler implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-CHANGECATEGORY")
        || usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
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

    tmp = PersonnelDB.getPersonnel(pid);

    
    path = request.getParameter("referrer");
    
    if(request.getParameter("category") == null)
    {
      throw new AdminException("Category ID Required.");
    }
    else
    {
      catid = Integer.parseInt(request.getParameter("category"));
    }
    
    cat = PersonnelCategoryDB.getPersonnelCategory(catid);
    tmp.setPersonnelCategory(cat);

    if(cat.getPersonnelCategoryName().equalsIgnoreCase("TEACHER"))
    {
      tmp.setViewOnNextLogon("PROFILE");
    }
    else if(cat.getPersonnelCategoryName().equalsIgnoreCase("RETIRED EMPLOYEE"))
    {
      tmp.setSchool(null);
    }
    else
    {
      tmp.setViewOnNextLogon(null);
    }
    
    return path;
  }
}