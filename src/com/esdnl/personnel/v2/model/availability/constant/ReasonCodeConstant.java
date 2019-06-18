package com.esdnl.personnel.v2.model.availability.constant;

public class ReasonCodeConstant  
{
  private int uid;
	private String desc;
  
  public static final ReasonCodeConstant REASON_1 
    = new ReasonCodeConstant(1, "Could Not Be Reached");
  
  public static final ReasonCodeConstant REASON_2 
  = new ReasonCodeConstant(2, "Indicated Unavailable");
  
  public static final ReasonCodeConstant REASON_3 
  = new ReasonCodeConstant(3, "Previously Booked");
    
  public static final ReasonCodeConstant[] ALL = new ReasonCodeConstant[]
  {
    REASON_1,
    REASON_2,
    REASON_3
  };
  
  private ReasonCodeConstant(int uid, String desc)
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
    return this.desc;
  }
  
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof ReasonCodeConstant)
    {
      check = (this.getId()==((ReasonCodeConstant)obj).getId());
    }
    
    return check;
  }
  
  public static final ReasonCodeConstant get(int uid)
  {
  	ReasonCodeConstant tmp = null;
    
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