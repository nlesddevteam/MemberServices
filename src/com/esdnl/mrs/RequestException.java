package com.esdnl.mrs;

import javax.servlet.*;


public class RequestException extends ServletException
{
	private static final long serialVersionUID = -5339933733729977056L;

	public RequestException(String msg)
  {
    super(msg);
  }
}