package com.esdnl.complaint.model.bean;

public class ComplaintException extends Exception
{
  public ComplaintException(String msg)
  {
    super(msg);
  }
  
  public ComplaintException(String msg, Throwable e)
  {
    super(msg, e);
  }
}