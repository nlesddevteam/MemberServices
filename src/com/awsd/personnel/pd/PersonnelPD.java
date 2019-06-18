package com.awsd.personnel.pd;

import java.util.*;


public class PersonnelPD 
{
  private int pd_id;
  private String pd_title;
  private String pd_desc;
  private Date pd_startdate;
  private Date pd_finishdate;
 
  public PersonnelPD(int pd_id, String pd_title, String pd_desc, Date pd_startdate, Date pd_finishdate)
  {
    this.pd_id = pd_id;
    this.pd_title = pd_title;
    this.pd_desc = pd_desc;
    this.pd_startdate = pd_startdate;
    this.pd_finishdate = pd_finishdate;
  }
  
  public PersonnelPD(String pd_title, String pd_desc, Date pd_startdate, Date pd_finishdate)
  {
    this(-1,  pd_title, pd_desc, pd_startdate, pd_finishdate);
  }
  
  public int getID()
  {
    return this.pd_id;
  }
  
  public void setID(int pd_id)
  {
    this.pd_id = pd_id;
  }
  
  public String getTitle()
  {
    return this.pd_title;
  }
  
  public String getDescription()
  {
    return this.pd_desc;
  }
  
  public Date getStartDate()
  {
    return this.pd_startdate;
  }
  
  public Date getFinishDate()
  {
    return this.pd_finishdate;
  }
}