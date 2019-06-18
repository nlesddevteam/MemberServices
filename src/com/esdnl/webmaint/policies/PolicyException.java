package com.esdnl.webmaint.policies;

import javax.servlet.*;


public class PolicyException  extends ServletException
{
  public PolicyException(String msg)
  {
    super(msg);
  }
}