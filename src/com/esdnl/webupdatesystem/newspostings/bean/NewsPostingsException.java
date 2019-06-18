package com.esdnl.webupdatesystem.newspostings.bean;

public class NewsPostingsException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public NewsPostingsException (String msg)
	 {
	    super(msg);
	 }
	  
	 public NewsPostingsException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public NewsPostingsException(Throwable cause)
	 {
	    super(cause);
	 }
}