package com.esdnl.sacs.model.bean;

import com.awsd.school.*;

public class SchoolInitiativeBean 
{
  private School s;
  private InitiativeBean init;
  private String desc;
  
  public SchoolInitiativeBean()
  {
  }
  
  public void setSchool(School s)
  {
    this.s = s;
  }
  
  public School getSchool()
  {
    return this.s;
  }
  
  public void setInitiative(InitiativeBean init)
  {
    this.init = init;
  }
  
  public InitiativeBean getInitiative()
  {
    return this.init;
  }
  
  public void setDescription(String desc)
  {
    this.desc = desc;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
}