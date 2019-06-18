package com.awsd.efile.equestion.handler;

import com.awsd.efile.*;
import com.awsd.efile.equestion.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewEFileQuestionSearchResultsRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String days = null, types[] = null, grades[] = null, subjects[] = null, courses[] = null;;
    StringTokenizer st = null;
    boolean haveCriteria = false;
    SearchResults results = null;
    

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

    if(request.getParameter("page") != null)
    {
      path = "searchResults.jsp?page=" + request.getParameter("page");
    }
    else
    {
      days = request.getParameter("searchByDate");
      if(days != null)
      {
        haveCriteria = true;
      }
    
      types = request.getParameterValues("questionType");
      if(types != null)
      {
        haveCriteria = true;  
      }

      grades = request.getParameterValues("gradeLevel");
      if(grades != null)
      {
        haveCriteria = true;  
      }

      subjects = request.getParameterValues("documentSubject");
      if(subjects != null)
      {
        haveCriteria = true;  
      }

      courses = request.getParameterValues("documentCourse");
      if(courses != null)
      {
        haveCriteria = true;  
      }

      if(!haveCriteria)
      {
        throw new EFileException("At least one search criteria must be entered.");
      }

    
      results = QuestionDB.search(usr, days, types, grades, subjects, courses, 15);
    
      session.setAttribute("results", results);

      if(((HashMap)session.getAttribute("EQUESTION-CART"))== null)
        session.setAttribute("EQUESTION-CART", new HashMap(10));

      //System.err.println("Returned - " + docs.size());
    
      path = "searchResults.jsp?page=1";
    }
    
    return path;
  }
}