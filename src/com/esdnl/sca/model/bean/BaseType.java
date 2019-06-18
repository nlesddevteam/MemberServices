package com.esdnl.sca.model.bean;

public class BaseType 
{
  private int id;
  private String desc;
  
  public int getId()
  {
    return this.id;
  }
  
  public Integer getIdObj()
  {
    return new Integer(id);
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
  
  public void setDescription(String desc)
  {
    this.desc = desc;
  }
  
  public boolean equals(Object o)
  {
    return false;
  }
}