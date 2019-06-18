package com.esdnl.personnel.jobs.bean;


public class JobOpportunityException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5879279303181507981L;

	public JobOpportunityException(String msg)
  {
    super(msg);
  }
  
  public JobOpportunityException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public JobOpportunityException(Throwable cause)
  {
    super(cause);
  }
}