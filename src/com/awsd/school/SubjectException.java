package com.awsd.school;

import javax.servlet.*;


public class SubjectException extends ServletException
{
  public SubjectException(String msg)
  {
    super(msg);
  }
}