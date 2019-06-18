package com.esdnl.complaint.model.bean;

import com.awsd.personnel.*;

import com.esdnl.complaint.model.bean.*;
import com.esdnl.complaint.model.constant.*;

import java.util.*;
import java.text.*;

public class ComplaintHistoryBean 
{
  private int id;
  private ComplaintBean complaint;
  private ComplaintStatus action;
  private Personnel by_who;
  private Personnel to_who;
  private Date history_date;
  
  public ComplaintHistoryBean()
  {
    id = -1;
    complaint = null;
    action = null;
    by_who = null;
    to_who = null;
    history_date = null;
  }
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public ComplaintBean getComplaint()
  {
    return this.complaint;
  }
  
  public void setComplaint(ComplaintBean complaint)
  {
    this.complaint = complaint;
  }
  
  public Personnel getByWho()
  {
    return this.by_who;
  }
  
  public void setByWho(Personnel by_who)
  {
    this.by_who = by_who;
  }
  
  public Personnel getToWho()
  {
    return this.to_who;
  }
  
  public void setToWho(Personnel to_who)
  {
    this.to_who = to_who;
  }
  
  public Date getHistoryDate()
  {
    return this.history_date;
  }
  
  public void setHistoryDate(Date history_date)
  {
    this.history_date = history_date;
  }
  
  public ComplaintStatus getAction()
  {
    return this.action;
  }
  
  public void setAction(ComplaintStatus action)
  {
    this.action = action;
  }
  
  public String toString()
  {
    return (new SimpleDateFormat("dd/MM/yyyy hh:mm a")).format(this.history_date) + " - " + action.getDescription();
  }
}