package com.esdnl.webmaint.ceoweb.bean;

import java.util.*;

public class SchoolVisitBean 
{
  private int visit_id;
  private String imgfile;
  private String caption;
  private Date visit_date;
  
  public SchoolVisitBean()
  {
    this.visit_id = -1;
    this.imgfile = null;
    this.caption = null;
    this.visit_date = null;
  }
  
  public void setVisitID(int visit_id)
  {
    this.visit_id = visit_id;
  }
  
  public int getVisitID()
  {
    return this.visit_id;
  }
  
  public void setImageFileName(String imgfile)
  {
    this.imgfile = imgfile;
  }
  
  public String getImageFileName()
  {
    return this.imgfile;
  }
  
  public void setCaption(String caption)
  {
    this.caption = caption;
  }
  
  public String getCaption()
  {
    return this.caption;
  }
  
  public void setVisitDate(Date visit_date)
  {
    this.visit_date = visit_date;
  }
  
  public Date getVisitDate()
  {
    return this.visit_date;
  }
}