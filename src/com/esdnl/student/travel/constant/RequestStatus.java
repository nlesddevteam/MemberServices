package com.esdnl.student.travel.constant;

public class RequestStatus 
{
  public static RequestStatus SUBMITTED = new RequestStatus(1, "SUBMITTED");
  public static RequestStatus APPROVED = new RequestStatus(2, "APPROVED");
  public static RequestStatus REJECTED = new RequestStatus(3, "REJECTED");
  public static RequestStatus[] ALL = new RequestStatus[]
  {
    SUBMITTED, 
    APPROVED,
    REJECTED
  };
  
  private int value;
  private String desc;
  
  private RequestStatus(int value, String desc)
  {
    this.value = value;
    this.desc = desc;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
  
  public static RequestStatus get(int value)
  {
    RequestStatus tmp = null;
    
    for(int i=0; i < ALL.length; i++)
    {
      if(ALL[i].getValue() == value)
      {
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
  
  public boolean equals(Object o)
  {
    boolean check = true;
    
    if(!(o instanceof RequestStatus))
      check = false;
    else if(((RequestStatus)o).getValue() != this.getValue())
      check = false;
    
    return check;
  }
  
  public String toString()
  {
    return this.getDescription();
  }
}