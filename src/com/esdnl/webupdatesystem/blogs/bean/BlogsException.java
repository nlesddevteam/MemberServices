package com.esdnl.webupdatesystem.blogs.bean;

public class BlogsException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public BlogsException (String msg)
	 {
	    super(msg);
	 }
	  
	 public BlogsException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public BlogsException(Throwable cause)
	 {
	    super(cause);
	 }
}
