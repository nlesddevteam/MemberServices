package com.esdnl.personnel.jobs.reports;


public class ReportException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5879279303181507981L;

	public ReportException(String msg)
  {
    super(msg);
  }
  
  public ReportException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public ReportException(Throwable cause)
  {
    super(cause);
  }
}