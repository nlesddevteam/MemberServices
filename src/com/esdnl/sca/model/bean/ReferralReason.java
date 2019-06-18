package com.esdnl.sca.model.bean;

public class ReferralReason extends BaseType
{
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof ReferralReason)
    {
      check = (this.getId() ==((ReferralReason)obj).getId());
    }
    
    return check;
  }  
}