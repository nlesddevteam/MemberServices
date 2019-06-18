package com.awsd.personnel.pd;

import javax.servlet.*;

public class PDException extends ServletException 
{
  public PDException()
  {
    super();
  }
  
  public PDException(String msg)
  {
    super(msg);
  }
}