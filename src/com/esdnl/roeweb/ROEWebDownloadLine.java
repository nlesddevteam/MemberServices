package com.esdnl.roeweb;

import java.util.*;
import java.text.*;

public class ROEWebDownloadLine 
{
  private String sin;
  private Date dateIssued;
  private Date lastDayWorked;
  
  public ROEWebDownloadLine(String[] data) throws Exception
  {
    this.sin = data[1].trim();
    this.dateIssued = getDate(data[0]);
    this.lastDayWorked = getDate(data[2]);
  }
  
  public String getSin()
  {
    return this.sin;
  }
  
  public Date getDateIssued()
  {
    return this.dateIssued;
  }
  
  public Date getLastDayWorked()
  {
    return this.lastDayWorked;
  }
  
  private Date getDate(String data) throws Exception
  {
    SimpleDateFormat sdf_in_d1 = new SimpleDateFormat("dMMyyyy");
    SimpleDateFormat sdf_in_d2 = new SimpleDateFormat("ddMMyyyy");
    Date tmp = null;
    
    if(data.trim().length() == 7)
      tmp = sdf_in_d1.parse(data.trim());
    else
      tmp = sdf_in_d2.parse(data.trim());
    
    return tmp;
  }
  
  public String toString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    
    return this.sin + "," + sdf.format(this.lastDayWorked) + "," + sdf.format(this.dateIssued);
  }
}