package com.awsd.strike;

import javax.servlet.*;


public class StrikeException extends ServletException
{
  public StrikeException(String msg)
  {
    super(msg);
  }
}