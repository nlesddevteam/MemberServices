package com.awsd.efile.edocument;

import javax.servlet.*;


public class DocumentException  extends ServletException
{
  public DocumentException(String msg)
  {
    super(msg);
  }
}