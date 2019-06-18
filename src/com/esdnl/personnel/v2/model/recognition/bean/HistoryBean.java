package com.esdnl.personnel.v2.model.recognition.bean;

import com.esdnl.personnel.v2.model.recognition.constant.RequestStatus;
import java.util.Date;
import com.awsd.personnel.*;

public class HistoryBean 
{
  private int Id;
  private int requestId;
  private RequestStatus status;
  private Date actionDate;
  private String comments;
  private int actionedById;
  private Personnel actionedByObj;

  public HistoryBean()
  {
    Id = 0;
    requestId = 0;
    status = null;
    actionDate = null;
    comments = null;
    actionedById = 0;
    actionedByObj = null;
  }

  public int getId()
  {
    return Id;
  }

  public void setId(int newId)
  {
    Id = newId;
  }

  public int getRequestId()
  {
    return requestId;
  }

  public void setRequestId(int newRequestId)
  {
    requestId = newRequestId;
  }

  public RequestStatus getStatus()
  {
    return status;
  }

  public void setStatus(RequestStatus newStatus)
  {
    status = newStatus;
  }

  public Date getActionDate()
  {
    return actionDate;
  }

  public void setActionDate(Date newActionDate)
  {
    actionDate = newActionDate;
  }

  public String getComments()
  {
    return comments;
  }

  public void setComments(String newComments)
  {
    comments = newComments;
  }
  
  public int getActionedById()
  {
    return this.actionedById;
  }
  
  public void setActionedById(int actionedById)
  {
    this.actionedById = actionedById;
  }
  
  public Personnel getActionedByObject()
  {
    
    if((this.actionedByObj == null) && (this.actionedById > 0))
    {
      try
      {
        this.actionedByObj = PersonnelDB.getPersonnel(this.actionedById);
        this.actionedById = this.actionedByObj.getPersonnelID();
      }
      catch(PersonnelException e)
      {
        this.actionedByObj = null;
        this.actionedById = 0;
      }
    }
      
    return this.actionedByObj;
  }
}