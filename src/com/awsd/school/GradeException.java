package com.awsd.school;

import javax.servlet.*;


public class GradeException extends ServletException
{
  public GradeException(String msg)
  {
    super(msg);
  }
}