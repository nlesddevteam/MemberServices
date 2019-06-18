package com.esdnl.webmaint.ceoweb.bean;

import java.util.*;

public class DirectorReportBean
{
  private Date report_date;
  private String report_title;
  
  public DirectorReportBean()
  {
    report_date = null;
    report_title = null;
  }
  
  public Date getReportDate()
  {
    return this.report_date;
  }
  
  public void setReportDate(Date report_date)
  {
    this.report_date = report_date;
  }
  
  public String getReportTitle()
  {
    return this.report_title;
  }
  
  public void setReportTitle(String report_title)
  {
    this.report_title = report_title;
  }
}