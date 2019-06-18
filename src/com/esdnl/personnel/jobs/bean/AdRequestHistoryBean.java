package com.esdnl.personnel.jobs.bean;

import com.awsd.personnel.*;
import com.esdnl.personnel.jobs.constants.*;
import java.util.*;
import java.text.*;

public class AdRequestHistoryBean 
{
  
  public static final String DATE_FORMAT = "dd/MM/yyyy";
  
  private int history_id;
  private int request_id;
  private RequestStatus status;
  private Date history_date;
  private Personnel p;
  private String comment;
  
  public AdRequestHistoryBean()
  {
    this.history_id = 0;
    this.request_id = 0;
    this.status = null;
    this.history_date = null;
    this.p = null;
    this.comment = null;
  }
  
  public int getId()
  {
    return this.history_id;
  }
  
  public void setId(int history_id)
  {
    this.history_id = history_id;
  }
  
  public int getRequestId()
  {
    return this.request_id;
  }
  
  public void setRequestId(int request_id)
  {
    this.request_id = request_id;
  }
  
  public RequestStatus getStatus()
  {
    return this.status;
  }
  
  public void setRequestStatus(RequestStatus status)
  {
    this.status = status;
  }
  
  public Date getHistoryDate()
  {
    return this.history_date;
  }
  
  public void setHistoryDate(Date history_date)
  {
    this.history_date = history_date;
  }
  
  public String getFormatedHistoryDate()
  {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    
    return sdf.format(this.history_date);
  }
  
  public Personnel getPersonnel()
  {
    return this.p;
  }
  
  public void setPersonnel(Personnel p)
  {
    this.p = p;
  }
  
  public String getComments()
  {
    return this.comment;
  }
  
  public void setComments(String comment)
  {
    this.comment = comment;
  }
}