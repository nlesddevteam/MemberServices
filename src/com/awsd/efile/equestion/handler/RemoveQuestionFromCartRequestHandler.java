package com.awsd.efile.equestion.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class RemoveQuestionFromCartRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    HashMap cart = null;
    
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

    cart = (HashMap) session.getAttribute("EQUESTION-CART");
    if(cart == null)
    {
      session.setAttribute("EQUESTION-CART", new HashMap(10));
    }
    else
    {
      cart.remove(new Integer(Integer.parseInt(request.getParameter("qid"))));
    }
    
    path = "questionCart.jsp?page=" + request.getParameter("page");
    
    return path;
  }
}