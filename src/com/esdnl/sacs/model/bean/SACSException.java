package com.esdnl.sacs.model.bean;

public class SACSException extends Exception
{
  public SACSException(String msg)
  {
    super(msg);
  }
  
  public SACSException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public SACSException(Throwable cause)
  {
    super(cause);
  }
}
