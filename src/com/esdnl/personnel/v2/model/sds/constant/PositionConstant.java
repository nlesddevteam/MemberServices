package com.esdnl.personnel.v2.model.sds.constant;


public class PositionConstant 
{
  private String uid;
  
  public static final PositionConstant CASUAL_STUDENT_ASSISTANT 
    = new PositionConstant("Casual Student Assistant");
    
  public static final PositionConstant[] ALL = new PositionConstant[]
  {
    CASUAL_STUDENT_ASSISTANT  
  };
  
  private PositionConstant(String uid)
  {
    this.uid = uid;
  }
  
  public String getId()
  {
    return this.uid;
  }
    
  public String toString()
  {
    return this.uid;
  }
  
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof PositionConstant)
    {
      check = this.getId().equals(((PositionConstant)obj).getId());
    }
    
    return check;
  }
  
  public static final PositionConstant get(String uid)
  {
    PositionConstant tmp = null;
    
    for(int i=0; i < ALL.length; i ++)
    {
      if(ALL[i].getId().equals(uid))
      { 
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
}