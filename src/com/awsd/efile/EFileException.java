package com.awsd.efile;

import javax.servlet.*;


public class EFileException extends ServletException
{ 
  public EFileException(String reason)
  {
    super(reason);
  }
}