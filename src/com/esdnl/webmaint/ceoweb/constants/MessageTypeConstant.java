package com.esdnl.webmaint.ceoweb.constants;

public class MessageTypeConstant 
{
  public static final MessageTypeConstant MSG_STAFF = new MessageTypeConstant(1, "MESSAGES TO STAFF");
  public static final MessageTypeConstant MSG_COMMUNITY = new MessageTypeConstant(2, "MESSAGES TO COMMUNITY");
  
  public static final MessageTypeConstant[] ALL = new MessageTypeConstant[]{MSG_STAFF, MSG_COMMUNITY};
  
  private int type_id;
  private String desc;
  
  private  MessageTypeConstant(int type_id, String desc)
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
    if(obj instanceof MessageTypeConstant)
    {
      if(this.getTypeID() == ((MessageTypeConstant)obj).getTypeID())
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
  
  public static MessageTypeConstant get(int type_id)
  {
    MessageTypeConstant tmp = null;
    
    for(int i = 0; i < ALL.length; i++)
    {
      if(ALL[i].getTypeID() == type_id)
      {
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
}