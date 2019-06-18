package com.awsd.travel;

import javax.servlet.*;


public class TravelClaimException extends ServletException
{
  public TravelClaimException(String msg)
  {
    super(msg);
  }
}