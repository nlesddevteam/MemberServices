package com.awsd.travel;

import com.awsd.personnel.*;

import java.util.*;


public class HistoryItem 
{
  private int history_id;
  private int personnel_id;
  private Date history_date;
  private String action;
  
  public HistoryItem(int history_id, int personnel_id, Date history_date, String action)
  {
    this.history_id = history_id;
    this.personnel_id = personnel_id;
    this.history_date = history_date;
    this.action = action;
  }

  public HistoryItem(int personnel_id, Date history_date, String action)
  {
    this(-1, personnel_id, history_date, action);
  }


  public int HistoryID()
  {
    return this.history_id;
  }

  public Personnel getPerformedBy()
  {
    Personnel tmp = null;
    if(personnel_id > 0)
    {
      try
      {
        tmp = PersonnelDB.getPersonnel(this.personnel_id);
      }
      catch(PersonnelException e)
      {
        tmp = null;
      }
    }
    else
    {
      tmp = null;
    }
    return tmp;
  }

  public String getActionPerformed()
  {
    return this.action;
  }

  public Date getDatePerformed()
  {
    return this.history_date;
  }
}