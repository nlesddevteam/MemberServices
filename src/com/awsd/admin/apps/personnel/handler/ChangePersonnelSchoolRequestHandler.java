package com.awsd.admin.apps.personnel.handler;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.school.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ChangePersonnelSchoolRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel tmp = null;
    PersonnelCategory cat = null;
    School s = null;
    int pid, sid;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-CHANGESCHOOL")
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
    if(path == null)
    {
      if(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW"))
        path = "personnel_admin_view.jsp";
      else
        throw new AdminException("REFERRER REQUIRED.");
    }
    
    if(request.getParameter("sid") == null)
    {
      throw new AdminException("School ID Required.");
    }
    else
    {
      sid = Integer.parseInt(request.getParameter("sid"));
    }
    
    if(sid == -1)
      tmp.setSchool(null);
    else
    {
      s = SchoolDB.getSchool(sid);
      tmp.setSchool(s);
    }
    
    if(tmp.getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("TEACHER"))
    {
      tmp.setViewOnNextLogon("PROFILE");
    }
    else
    {
      tmp.setViewOnNextLogon(null);
    }
    
    return path;
  }
}