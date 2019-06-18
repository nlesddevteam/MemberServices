package com.awsd.school;

import javax.servlet.*;


public class SchoolFamilyException extends ServletException
{
  public SchoolFamilyException(String msg)
  {
    super(msg);
  }
}