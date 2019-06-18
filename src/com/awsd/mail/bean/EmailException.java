package com.awsd.mail.bean;

public class EmailException extends Exception
{
 
	private static final long serialVersionUID = 269670838648953412L;

	public EmailException(String msg)
  {
    super(msg);
  }
  
  public EmailException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public EmailException(Throwable cause)
  {
    super(cause);
  }
}