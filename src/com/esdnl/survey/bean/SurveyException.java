package com.esdnl.survey.bean;

public class SurveyException extends Exception{
	
	private static final long serialVersionUID = 5328387717585351107L;

	public SurveyException(String msg)
  {
    super(msg);
  }
  
  public SurveyException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public SurveyException(Throwable cause)
  {
    super(cause);
  }
	
}
