package com.esdnl.personnel.jobs.constants;

public class RequestStatus 
{
  private int uid;
  private String desc;
  
  public static final RequestStatus SUBMITTED 
    = new RequestStatus(1, "SUBMITTED");
  
  public static final RequestStatus APPROVED 
    = new RequestStatus(2, "APPROVED");
    
  public static final RequestStatus REJECTED 
    = new RequestStatus(3, "REJECTED");
  
  public static final RequestStatus POSTED 
    = new RequestStatus(4, "POSTED");
  
  public static final RequestStatus PREDISPLAYED 
  = new RequestStatus(5, "PREDISPLAYED");
  
  public static final RequestStatus CANCELLED 
  = new RequestStatus(6, "CANCELLED");
    
  public static final RequestStatus[] ALL = new RequestStatus[]
  {
    SUBMITTED,
    APPROVED,
    REJECTED,
    POSTED,
    PREDISPLAYED,
    CANCELLED
  };
  
  private RequestStatus(int uid, String desc)
  {
    this.uid = uid;
    this.desc = desc;
  }
  
  public int getId()
  {
    return this.uid;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
    
  public String toString()
  {
    return this.getDescription();
  }
  
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof RequestStatus)
    {
      check = (this.getId() ==((RequestStatus)obj).getId());
    }
    
    return check;
  }
  
  public static final RequestStatus get(int uid)
  {
    RequestStatus tmp = null;
    
    for(int i=0; i < ALL.length; i ++)
    {
      if(ALL[i].getId() == uid)
      { 
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
}