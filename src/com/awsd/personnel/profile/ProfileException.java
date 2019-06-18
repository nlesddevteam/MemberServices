package com.awsd.personnel.profile;

import javax.servlet.*;


public class ProfileException extends ServletException 
{
  public ProfileException(String message)
  {
    super(message);
  }
}