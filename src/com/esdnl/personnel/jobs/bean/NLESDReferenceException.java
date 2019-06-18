package com.esdnl.personnel.jobs.bean;
public class NLESDReferenceException extends Exception {
private static final long serialVersionUID = 5879279303181507981L;

 public NLESDReferenceException(String msg)
 {
    super(msg);
 }
  
 public NLESDReferenceException(String msg, Throwable cause)
 {
    super(msg, cause);
 }
  
  public NLESDReferenceException(Throwable cause)
 {
    super(cause);
 }
}
