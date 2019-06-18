package com.esdnl.nicep.beans;

public class NICEPException extends Exception
{
  public NICEPException(String msg)
  {
    super(msg);
  }
  
  public NICEPException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public NICEPException(Throwable cause)
  {
    super(cause);
  }
}