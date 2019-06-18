package com.awsd.efile.edocument;

import javax.servlet.*;


public class DocumentTypeException extends ServletException
{
  public DocumentTypeException(String msg)
  {
    super(msg);
  }
}