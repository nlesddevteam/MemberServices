package com.esdnl.webmaint.highlights.beans;

import javax.servlet.*;

public class HighlightsException extends ServletException
{
  public HighlightsException(String msg)
  {
    super(msg);
  }
}