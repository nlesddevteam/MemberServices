package com.esdnl.webupdatesystem.programs.bean;

public class ProgramsException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public ProgramsException (String msg)
	 {
	    super(msg);
	 }
	  
	 public ProgramsException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public ProgramsException(Throwable cause)
	 {
	    super(cause);
	 }
}
