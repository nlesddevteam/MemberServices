package com.esdnl.webmaint.ceoweb.bean;

import javax.servlet.*;

public class CeoWebException extends ServletException 
{
  public CeoWebException(String msg)
  {
    super(msg);
  }
  
  public CeoWebException(String msg, Throwable e)
  {
    super(msg, e);
  }
}