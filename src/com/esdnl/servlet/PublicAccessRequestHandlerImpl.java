package com.esdnl.servlet;

import com.awsd.servlet.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PublicAccessRequestHandlerImpl extends RequestHandlerImpl 
implements LoginNotRequiredRequestHandler
{
  public PublicAccessRequestHandlerImpl()
  {
    super();
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException, IOException
  {
  	session = request.getSession(true);
  	
    form = new Form(request);
    
    ROOT_DIR = new File(session.getServletContext().getRealPath("/"));
    
    return null;
  }
}