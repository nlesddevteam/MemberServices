package com.awsd.financial;

import javax.servlet.*;


public class FinancialException extends ServletException
{
  public FinancialException(String msg)
  {
    super(msg);
  }
}