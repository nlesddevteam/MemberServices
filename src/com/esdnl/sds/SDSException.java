package com.esdnl.sds;

import javax.servlet.*;


public class SDSException  extends ServletException
{
  public SDSException(String msg)
  {
    super(msg);
  }
}