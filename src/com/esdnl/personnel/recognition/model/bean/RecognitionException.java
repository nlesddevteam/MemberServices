package com.esdnl.personnel.recognition.model.bean;

public class RecognitionException extends Exception
{
	private static final long serialVersionUID = 6229738724416373363L;

	public RecognitionException(String msg) 
  {
    super(msg);
  }
  
  public RecognitionException(String msg, Throwable e)
  {
    super(msg, e);
  }
}