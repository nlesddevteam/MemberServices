package com.esdnl.webmaint.common.bean;

public abstract class MessageTypeBean 
{
  private int type_id;
  private String desc;
  
  public  MessageTypeBean(int type_id, String desc)
  {
    this.type_id = type_id;
    this.desc = desc;
  }
  
  public int getTypeID()
  {
    return this.type_id;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
  
  public boolean equals(Object obj)
  {
    if(obj instanceof MessageTypeBean)
    {
      if(this.getTypeID() == ((MessageTypeBean)obj).getTypeID())
        return true;
      else
        return false;
    }
    else
      return false;
  }
  
  public String toString()
  {
    return this.getDescription();
  }
}