package com.awsd.admin.apps.personnel.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SchoolFamilyAdminRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    String op = "";
    int family_id, ps_id, sch_id;
    String family_name;
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

    path = "schoolfamilyadmin.jsp";
        
    op = request.getParameter("op");
    if(op == null)
    {
      throw new SchoolFamilyException("OP parameter is required.");
    }

    if(op.equalsIgnoreCase("add"))
    {
      family_name =  request.getParameter("family_name");
      if(family_name == null)
      {
        throw new SchoolFamilyException("DESC parameter is required for ADD operation.");
      }

      if(request.getParameter("ps_id")!= null)
      {
        ps_id = Integer.parseInt(request.getParameter("ps_id"));
      }
      else
      {
        ps_id = -1;
      }


      school_id = request.getParameterValues("schools");
      if((school_id == null) || (school_id.length == 0))
      {
        throw new SchoolFamilyException("SCHOOLS parameter is required for MOD operation.");
      }

      
      family_id = SchoolFamilyDB.addSchoolFamily(new SchoolFamily(ps_id, family_name), school_id);
      if(family_id == -1)
      {
        throw new SchoolFamilyException("Could not add School System to DB.");
      }
    }
    else if(op.equalsIgnoreCase("del"))
    {   
      if(request.getParameter("family_id")!= null)
      {
        family_id = Integer.parseInt(request.getParameter("family_id"));
      }
      else
      {
        throw new SchoolFamilyException("Family ID parameter is required for DEL operation.");
      }
      
      SchoolFamilyDB.deleteSchoolFamily(family_id);
    }
    else if(op.equalsIgnoreCase("delsch"))
    {
      
      if(request.getParameter("family_id")!= null)
      {
        family_id = Integer.parseInt(request.getParameter("family_id"));
      }
      else
      {
        throw new SchoolFamilyException("ID parameter is required for DEL operation.");
      }

      if(request.getParameter("sch_id")!= null)
      {
        sch_id = Integer.parseInt(request.getParameter("sch_id"));
      }
      else
      {
        throw new SchoolFamilyException("School ID parameter is required for DELSCH operation.");
      }

      SchoolFamilyDB.deleteSchoolFamilySchool(family_id, sch_id);
    }
    else if(op.equalsIgnoreCase("mod"))
    {
      if(request.getParameter("family_id")!= null)
      {
        family_id = Integer.parseInt(request.getParameter("family_id"));
      }
      else
      {
        throw new SchoolFamilyException("ID parameter is required for MOD operation.");
      }

      if(request.getParameter("confirmed") == null)
      {
        request.setAttribute("FAMILY", SchoolFamilyDB.getSchoolFamily(family_id));

        path = "modifyschoolfamily.jsp";
      }
      else
      { 
        family_name =  request.getParameter("family_name");
        if(family_name == null)
        {
          throw new SchoolFamilyException("DESC parameter is required for MOD operation.");
        }

        if(request.getParameter("ps_id")!= null)
        {
          ps_id = Integer.parseInt(request.getParameter("ps_id"));
        }
        else
        {
          throw new SchoolFamilyException("ps_id parameter is required for MOD operation.");
        }

        school_id = request.getParameterValues("schools");
        if((school_id == null) || (school_id.length == 0))
        {
          throw new SchoolFamilyException("SCHOOLS parameter is required for MOD operation.");
        }
  
        SchoolFamilyDB.updateSchoolFamily(new SchoolFamily(family_id, ps_id, family_name), school_id);
      }
    }
    
    return path;
  }
}