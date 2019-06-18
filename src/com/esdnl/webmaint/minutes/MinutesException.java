package com.esdnl.webmaint.minutes;

import javax.servlet.*;


public class MinutesException extends ServletException
{
  public MinutesException(String msg)
  {
    super(msg);
  }
}