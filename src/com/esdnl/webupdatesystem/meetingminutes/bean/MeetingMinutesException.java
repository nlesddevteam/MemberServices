package com.esdnl.webupdatesystem.meetingminutes.bean;

public class MeetingMinutesException extends Exception{
	private static final long serialVersionUID = 5879279303181507981L;

	 public MeetingMinutesException (String msg)
	 {
	    super(msg);
	 }
	  
	 public MeetingMinutesException(String msg, Throwable cause)
	 {
	    super(msg, cause);
	 }
	  
	  public MeetingMinutesException(Throwable cause)
	 {
	    super(cause);
	 }
}
