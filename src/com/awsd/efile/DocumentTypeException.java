package com.awsd.efile;

import javax.servlet.*;

public class DocumentTypeException extends ServletException
{
  public DocumentTypeException(String msg)
  {
    super(msg);
  }
}