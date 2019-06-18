package com.esdnl.fund3.bean;

public class Fund3Exception extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public Fund3Exception (String msg)
	 {
	    super(msg);
	 }
	  
	 public Fund3Exception(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public Fund3Exception(Throwable cause)
	 {
	    super(cause);
	 }
}