package com.awsd.admin;

import javax.servlet.*;


public class AdminException extends ServletException 
{
  public AdminException(String msg)
  {
    super(msg);
  }
}