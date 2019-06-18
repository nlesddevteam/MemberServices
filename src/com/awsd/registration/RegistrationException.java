package com.awsd.registration;

import javax.servlet.*;


public class RegistrationException extends ServletException 
{
  public RegistrationException(String msg)
  {
    super(msg);
  }
}