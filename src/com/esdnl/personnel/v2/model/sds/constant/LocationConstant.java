package com.esdnl.personnel.v2.model.sds.constant;

public class LocationConstant 
{
  private String uid;
  
  public static final LocationConstant SUBSTITUTE_EASTERN_REGION 
    = new LocationConstant("Substitute - Eastern Region");
    
  public static final LocationConstant[] ALL = new LocationConstant[]
  {
    SUBSTITUTE_EASTERN_REGION  
  };
  
  private LocationConstant(String uid)
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
    
    if(obj instanceof LocationConstant)
    {
      check = this.getId().equals(((LocationConstant)obj).getId());
    }
    
    return check;
  }
  
  public static final LocationConstant get(String uid)
  {
    LocationConstant tmp = null;
    
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