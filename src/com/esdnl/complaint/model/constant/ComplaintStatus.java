package com.esdnl.complaint.model.constant;

public class ComplaintStatus 
{
  public static final ComplaintStatus SUBMITTED = new ComplaintStatus(1, "SUBMITTED");
  public static final ComplaintStatus REVIEWED = new ComplaintStatus(2, "REVIEWED");
  public static final ComplaintStatus REJECTED = new ComplaintStatus(3, "REJECTED");
  public static final ComplaintStatus ASSIGNED = new ComplaintStatus(4, "ASSIGNED");
  public static final ComplaintStatus RESOLVED = new ComplaintStatus(5, "RESOLVED");
  public static final ComplaintStatus NO_RESOLUTION = new ComplaintStatus(6, "NO RESOLUTION");
  
  public static final ComplaintStatus[] ALL = new ComplaintStatus[]
  {
    SUBMITTED, 
    REVIEWED, 
    ASSIGNED, 
    RESOLVED, 
    NO_RESOLUTION,
    REJECTED
  };
  
  private int value;
  private String desc;
  
  private ComplaintStatus(int value, String desc)
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
  
  public boolean equals(Object o)
  {
    boolean check = true;
    
    if(o == null)
      check = false;
    else if(!(o instanceof ComplaintStatus))
      check = false;
    else if(((ComplaintStatus)o).getValue() != this.getValue())
      check = false;
    else
      check = true;
      
    return check;
  }
  
  
  public String toString()
  {
    return this.getDescription();
  }
  
  public static ComplaintStatus get(int value)
  {
    ComplaintStatus tmp = null;
    
    switch(value)
    {
      case 1:
        tmp = SUBMITTED;
        break;
      case 2:
        tmp = REVIEWED;
        break;
      case 3:
        tmp = REJECTED;
        break;
      case 4:
        tmp = ASSIGNED;
        break;
      case 5:
        tmp = RESOLVED;
        break;
      case 6:
        tmp = NO_RESOLUTION;
        break;
      default:
        tmp = null;
    }
    
    return tmp;
  }
}