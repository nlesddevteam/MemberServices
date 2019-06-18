package com.awsd.efile.equestion.handler;

import com.awsd.efile.equestion.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddQuestionToCartRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    HashMap items = null;
    Question q = null;
    
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
    
    try
    {
      items = (HashMap)session.getAttribute("EQUESTION-CART");

      if(items == null)
        items = new HashMap(10);
        
      q = QuestionDB.getQuestion(Integer.parseInt((String)request.getParameter("qid")));

      if(q != null)
        items.put(new Integer(q.getQuestionID()), q);

      session.setAttribute("EQUESTION-CART", items);
    }
    catch(Exception e)
    {
      System.err.println(e);
      throw new QuestionException(e.toString());
    }
    
    path = ("searchResults.jsp?page="+((String)request.getParameter("page")));
    
    return path;
  }
}