package com.esdnl.webmaint.policies;

public class PolicyCategory 
{
  private String code;
  private String title;
  
  public PolicyCategory(String code, String title)
  {
    this.code = code;
    this.title = title;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public String toString()
  {
    return this.code;
  }
}