package com.esdnl.webupdatesystem.tenders.bean;

public class TenderException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public TenderException (String msg)
	 {
	    super(msg);
	 }
	  
	 public TenderException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public TenderException(Throwable cause)
	 {
	    super(cause);
	 }
}
