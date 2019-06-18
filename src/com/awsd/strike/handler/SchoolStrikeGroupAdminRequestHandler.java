package com.awsd.strike.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.strike.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SchoolStrikeGroupAdminRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    String op = "";
    int group_id, liason;
    String school_id[] = null;
    
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

    op = request.getParameter("op");
    if(op == null)
    {
      throw new StrikeException("OP parameter is required.");
    }

    if(op.equalsIgnoreCase("add"))
    {
      if(request.getParameter("liason")!= null)
      {
        liason = Integer.parseInt(request.getParameter("liason"));
      }
      else
      {
        throw new StrikeException("ID parameter is required for ADD operation.");
      }

      school_id = request.getParameterValues("schools");
      if((school_id == null) || (school_id.length == 0))
      {
        throw new StrikeException("SCHOOLS parameter is required for ADD operation.");
      }

      
      group_id = SchoolStrikeGroupDB.addSchoolStrikeGroup(new SchoolStrikeGroup(-1, liason), school_id);
      if(group_id == -1)
      {
        throw new StrikeException("Could not add School Strike Group to DB.");
      }
    }
    else if(op.equalsIgnoreCase("del"))
    {
      
      if(request.getParameter("group_id")!= null)
      {
        group_id = Integer.parseInt(request.getParameter("group_id"));
      }
      else
      {
        throw new StrikeException("ID parameter is required for DEL operation.");
      }
      
      boolean check = SchoolStrikeGroupDB.deleteSchoolStrikeGroup(group_id);
    }
    /*
    else if(op.equalsIgnoreCase("mod"))
    {
      if(request.getParameter("group_id")!= null)
      {
        group_id = Integer.parseInt(request.getParameter("group_id"));
      }
      else
      {
        throw new StrikeException("ID parameter is required for MOD operation.");
      }
      
      if(request.getParameter("liason")!= null)
      {
        liason = Integer.parseInt(request.getParameter("liason"));
      }
      else
      {
        throw new StrikeException("liason parameter is required for MOD operation.");
      }
      SchoolStrikeGroupDB.updateSchoolStrikeGroup(new SchoolStrikeGroup(group_id, liason));
    }
    */
    
    path = "strikegroupadmin.jsp";
    
    return path;
  }
}