package com.awsd.efile.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class EFileRegisterGradesRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Grades grades = null;
    Grades ugrades = null;
    Grade g = null, lvlI = null, lvlII = null, lvlIII = null;
    Courses courses = null;
    Iterator iter = null;
    boolean isLevel1=false, isLevel2=false, isLevel3=false, isLibrarian=false, isSpecialNeeds=false;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    grades = new Grades(usr);

    if(grades.isEmpty())
    {
      grades = new Grades(true);
      ugrades = new Grades(false);

      iter = grades.iterator();
      while(iter.hasNext())
      {
        g = (Grade)iter.next();
      
        if(request.getParameter("G"+g.getGradeID()) != null)
        {
          ugrades.add(g);
          if(g.getGradeName().equalsIgnoreCase("LEVEL I"))
          {
            isLevel1 = true;
            lvlI = g;
          }
          else if(g.getGradeName().equalsIgnoreCase("LEVEL II"))
          {
            isLevel2 = true;
            lvlII = g;
          }
          else if(g.getGradeName().equalsIgnoreCase("LEVEL III"))
          {
            isLevel3 = true;
            lvlIII = g;
          }
          else if(g.getGradeName().equalsIgnoreCase("LIBRARY"))
          {
            isLibrarian = true;
          }
          else if(g.getGradeName().equalsIgnoreCase("SPECIAL NEEDS"))
          {
            isSpecialNeeds = true;
          }
        }
      }

      GradeDB.addGradePersonnel(ugrades,usr.getPersonnel());

      if(isLevel1 || isLevel2 || isLevel3)
      {
        path = "Registration_Courses.jsp";

        request.setAttribute("Grades", new Grade[]{lvlI, lvlII, lvlIII});
      }
      else if(isLibrarian)
      {
        RoleDB.addRoleMembership(RoleDB.getRole("TEACHER-LIBRARIAN"), usr.getPersonnel());
        path = "choose.jsp"; 
      }
      else if(isSpecialNeeds)
      {
        RoleDB.addRoleMembership(RoleDB.getRole("TEACHER-SPECIAL NEEDS"), usr.getPersonnel());
        path = "choose.jsp"; 
      }
      else
      {
        path = "choose.jsp"; 
      }
    }
    else
    {
      iter = grades.iterator();
      while(iter.hasNext())
      {
        g = (Grade)iter.next();
      
        if(g.getGradeName().equalsIgnoreCase("LEVEL I"))
        {
          isLevel1 = true;
          lvlI = g;
        }
        else if(g.getGradeName().equalsIgnoreCase("LEVEL II"))
        {
          isLevel2 = true;
          lvlII = g;
        }
        else if(g.getGradeName().equalsIgnoreCase("LEVEL III"))
        {
          isLevel3 = true;
          lvlIII = g;
        }
      }

      if(isLevel1 || isLevel2 || isLevel3)
      {
        courses = new Courses(usr);
        if(courses.isEmpty())
        {
          path = "Registration_Courses.jsp";
          request.setAttribute("Grades", new Grade[]{lvlI, lvlII, lvlIII});
        }
        else
        {
          path = "choose.jsp";
        }
      }
      else
      {
        path = "choose.jsp"; 
      }
    }

    return path;
  }
}