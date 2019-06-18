package com.esdnl.webupdatesystem.policies.bean;

public class PoliciesException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public PoliciesException (String msg)
	 {
	    super(msg);
	 }
	  
	 public PoliciesException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public PoliciesException(Throwable cause)
	 {
	    super(cause);
	 }
}
