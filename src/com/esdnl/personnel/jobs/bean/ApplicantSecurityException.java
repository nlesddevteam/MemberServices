package com.esdnl.personnel.jobs.bean;

public class ApplicantSecurityException extends Exception
{
	  /**
		 * 
		 */
		private static final long serialVersionUID = 5879279303181507981L;

		public ApplicantSecurityException(String msg)
	  {
	    super(msg);
	  }
	  
	  public ApplicantSecurityException(String msg, Throwable cause)
	  {
	    super(msg, cause);
	  }
	  
	  public ApplicantSecurityException(Throwable cause)
	  {
	    super(cause);
	  }
	}
