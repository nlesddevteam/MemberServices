package com.esdnl.h1n1.bean;


public class H1N1Exception extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5879279303181507981L;

	public H1N1Exception(String msg)
  {
    super(msg);
  }
  
  public H1N1Exception(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public H1N1Exception(Throwable cause)
  {
    super(cause);
  }
}