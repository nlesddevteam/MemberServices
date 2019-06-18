package com.esdnl.photocopier.bean;

public class PhotocopierException extends Exception
{
  public PhotocopierException(String msg)
  {
    super(msg);
  }
  
  public PhotocopierException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public PhotocopierException(Throwable cause)
  {
    super(cause);
  }
}