package com.esdnl.complaint.model.constant;

public class PhoneType 
{
  public static final PhoneType HOME = new PhoneType(1, "Home");
  public static final PhoneType WORK = new PhoneType(2, "Work");
  public static final PhoneType CELL = new PhoneType(3,  "Cell");
  public static final PhoneType PAGER = new PhoneType(4, "Pager");
  
  public static final PhoneType ALL[] = new PhoneType[]
  {
    HOME, 
    WORK, 
    CELL, 
    PAGER
  };

  private int value;
  private String desc;
  
  private PhoneType(int value, String desc)
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
  
  public static PhoneType get(int value)
  {
    PhoneType tmp = null;
    
    switch(value)
    {
      case 1:
        tmp = HOME;
        break;
      case 2:
        tmp = WORK;
        break;
      case 3:
        tmp = CELL;
        break;
      case 4:
        tmp = PAGER;
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
    else if(!(o instanceof PhoneType))
      check = false;
    else if(((PhoneType) o).getValue() != this.getValue())
      check = false;
    else
      check = true;
    
    return check;
  }
  
  public String toString()
  {
    return this.getDescription();
  }
}