package com.esdnl.payadvice.bean;

public class NLESDPayAdviceException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public NLESDPayAdviceException (String msg)
	 {
	    super(msg);
	 }
	  
	 public NLESDPayAdviceException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public NLESDPayAdviceException(Throwable cause)
	 {
	    super(cause);
	 }
	
}
