package com.awsd.personnel.admin.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddSecretQuestionRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String question="", answer="";
    SecretQuestion sq = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("USER-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    if((request.getParameter("op") != null) && request.getParameter("op").equals("CONFIRMED"))
    {
      if(request.getParameter("question") == null)
      {
          request.setAttribute("msg", "Question is requried.");
      }
      else if(request.getParameter("answer") == null)
      {
          request.setAttribute("msg", "Answer is required.");
      }
      else
      {
        question = request.getParameter("question");
        answer = request.getParameter("answer");
        
        sq = SecretQuestionDB.getSecretQuestion(usr.getPersonnel());
        
        if(sq == null)
        {
          sq = new SecretQuestion(question, answer);
          SecretQuestionDB.setSecretQuestion(usr.getPersonnel(), sq);
        }
        else
        {
          sq.setSecretQuestion(question, answer);
          SecretQuestionDB.updateSecretQuestion(usr.getPersonnel(), sq);
        }

        request.setAttribute("msg", "Secret Question has been set.");
        request.setAttribute("SECRETQUESTION", sq);
      }
    }
    else
    {
      request.setAttribute("SECRETQUESTION", SecretQuestionDB.getSecretQuestion(usr.getPersonnel()));
    }
    
    path = "setSecretQuestion.jsp";

    return path;
  }
}