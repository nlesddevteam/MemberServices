package com.esdnl.webupdatesystem.banners.bean;

public class BannersException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public BannersException (String msg)
	 {
	    super(msg);
	 }
	  
	 public BannersException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public BannersException(Throwable cause)
	 {
	    super(cause);
	 }
}
