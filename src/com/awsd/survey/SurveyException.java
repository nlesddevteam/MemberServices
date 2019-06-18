package com.awsd.survey;

import javax.servlet.*;


public class SurveyException  extends ServletException
{
  public SurveyException(String msg)
  {
    super(msg);
  }
}