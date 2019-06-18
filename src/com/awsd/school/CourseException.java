package com.awsd.school;

import javax.servlet.*;


public class CourseException extends ServletException
{
  public CourseException(String msg)
  {
    super(msg);
  }
}