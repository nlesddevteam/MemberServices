package com.awsd.efile.equestion.handler;

import com.awsd.efile.*;
import com.awsd.efile.equestion.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class EFileDeleteQuestionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Question doc = null;
    boolean done = false;
    final int id;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")&& usr.getUserPermissions().containsKey("EFILE-DELETE-DOCUMENT")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("id") == null)
    {
      throw new EFileException("Question ID required for delete operation.");
    }

    doc = QuestionDB.getQuestion(Integer.parseInt(request.getParameter("id")));
    id = doc.getQuestionID();

    done = QuestionDB.deleteQuestion(doc);

    if(!done)     
    {
      throw new QuestionException("deleteQuestion: could not delete question with id=" + id);
    }

    path = "searchRepository.jsp";
      
    return path;
  }
}