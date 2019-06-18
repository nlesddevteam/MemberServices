package com.esdnl.webupdatesystem.meetinghighlights.bean;

public class MeetingHighlightsException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public MeetingHighlightsException (String msg)
	 {
	    super(msg);
	 }
	  
	 public MeetingHighlightsException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public MeetingHighlightsException(Throwable cause)
	 {
	    super(cause);
	 }
}

