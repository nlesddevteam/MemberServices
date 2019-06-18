package com.esdnl.sacs.model.bean;

public class InitiativeBean 
{
  private int id;
  private String name;
  private String desc;
  
  public InitiativeBean()
  {
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setDescription(String desc)
  {
    this.desc = desc;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
  
  public String toString()
  {
    return this.getName();
  }
}