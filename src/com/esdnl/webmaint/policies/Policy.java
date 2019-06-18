package com.esdnl.webmaint.policies;

import java.util.*;


public class Policy 
{
  private String cat_code;
  private String code;
  private String title;
  private Date upload_date;
  
  public Policy(String cat_code, String code, String title)
  {
    this.cat_code = cat_code;
    this.code = code;
    this.title = title;
    upload_date = null;
  }
  
  public Policy(String cat_code, String code, String title, Date upload_date)
  {
    this.cat_code = cat_code;
    this.code = code;
    this.title = title;
    this.upload_date = upload_date;
  }
  
  
  public String getCategoryCode()
  {
    return this.cat_code;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public Date getUploadDate()
  {
    return this.upload_date;
  }
  
  public String toString()
  {
    return cat_code + "-" + this.code + ": " + this.title;
  }
}