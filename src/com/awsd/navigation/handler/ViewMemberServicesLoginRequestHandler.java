package com.awsd.navigation.handler;

import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewMemberServicesLoginRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    if(request.getParameter("msg")!= null)
      request.setAttribute("msg", request.getParameter("msg"));
    
    path = "signin.jsp";
    
    return path;
  }
}