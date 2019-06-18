package com.awsd.admin.apps.personnel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewPersonnelRecordRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel p = null;
    int pid;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "] - Attemping to delete school.");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    try
    {
      pid = Integer.parseInt(request.getParameter("pid"));
    }
    catch(NumberFormatException e)
    {
      throw new PersonnelException("Personnel ID Required");
    }

    p = PersonnelDB.getPersonnel(pid);

    request.setAttribute("RECORD", p);
    
    path = "personnel_record_view.jsp";
    
    return path;
  }
}