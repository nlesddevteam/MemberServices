package com.awsd.efile;

import javax.servlet.*;

public class DocumentException  extends ServletException
{
  public DocumentException(String msg)
  {
    super(msg);
  }
}