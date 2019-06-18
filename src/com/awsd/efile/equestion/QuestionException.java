package com.awsd.efile.equestion;

import javax.servlet.*;


public class QuestionException extends ServletException
{
  public QuestionException(String msg)
  {
    super(msg);
  }
}