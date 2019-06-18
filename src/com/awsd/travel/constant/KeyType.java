package com.awsd.travel.constant;


public class KeyType
{
  private int value;
  private String desc;
  
  public static final KeyType ROLE = new KeyType(1, "ROLE");
  public static final KeyType USER = new KeyType(2, "USER");
  
  public static final KeyType[] ALL = new KeyType[]
  {
  	ROLE, 
  	USER
  };
  
  private KeyType(int value, String desc)
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
  
  public boolean equal(Object o)
  {
    if(!(o instanceof KeyType))
      return false;
    else
      return (this.getValue() == ((KeyType)o).getValue());
  }
  
  public static KeyType get(int value)
  {
  	KeyType tmp = null;
    
    for(int i=0; i < ALL.length; i ++)
    {
      if(ALL[i].getValue() == value)
      { 
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
  
  public String toString()
  {
    return this.getDescription();
  }
}