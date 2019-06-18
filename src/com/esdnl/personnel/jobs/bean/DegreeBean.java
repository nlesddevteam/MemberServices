package com.esdnl.personnel.jobs.bean;

public class DegreeBean 
{
  private String abbreviation;
  private String title;
  
  public DegreeBean(String abbreviation, String title)
  {
    this.abbreviation = abbreviation;
    this.title = title;
  }
  
  public String getAbbreviation()
  {
    return this.abbreviation;
  }
  
  public void setAbbreviation(String abbreviation)
  {
    this.abbreviation = abbreviation;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
}