package com.esdnl.sca.model.bean;

public class SCAException extends Exception
{
  public SCAException(String msg)
  {
    super(msg);
  }
  
  public SCAException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public SCAException(Throwable cause)
  {
    super(cause);
  }
}