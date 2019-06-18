package com.esdnl.complaint.model.constant;

public class ComplaintType 
{
  private int value;
  private String desc;
  
  public static final ComplaintType COMPLAINT_BUSSING = new ComplaintType(1, "Bussing");
  public static final ComplaintType COMPLAINT_HR = new ComplaintType(3, "Human Resources");
  public static final ComplaintType COMPLAINT_PROGRAMMING = new ComplaintType(4, "Programming/Special Services");
  public static final ComplaintType COMPLAINT_SCHOOL = new ComplaintType(5, "School Related");
  public static final ComplaintType COMPLAINT_OTHER = new ComplaintType(6, "Other");
  
  public static final ComplaintType[] ALL = 
    new ComplaintType[]{
      COMPLAINT_BUSSING,
      COMPLAINT_HR,
      COMPLAINT_PROGRAMMING, 
      COMPLAINT_SCHOOL,
      COMPLAINT_OTHER
    };
  
  private ComplaintType(int value, String desc)
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
  
  public static ComplaintType get(int value)
  {
    ComplaintType tmp = null;
    
    switch(value)
    {
      case 1:
        tmp = COMPLAINT_BUSSING;
        break;
      case 3:
        tmp = COMPLAINT_HR;
        break;
      case 4:
        tmp = COMPLAINT_PROGRAMMING;
        break;
      case 5:
        tmp = COMPLAINT_SCHOOL;
        break;
      case 6:
        tmp = COMPLAINT_OTHER;
        break;
      default:
        tmp = null;
    }
    
    return tmp;
  }

  public boolean equals(Object o)
  {
    boolean check = true;
    
    if(o == null)
      check = false;
    else if(!(o instanceof ComplaintType))
      check = false;
    else if(((ComplaintType)o).getValue() != this.getValue())
      check = false;
    
    return check;
  }
  
  public String toString()
  {
    return this.getDescription();
  }
}