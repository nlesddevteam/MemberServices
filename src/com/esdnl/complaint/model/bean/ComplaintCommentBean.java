package com.esdnl.complaint.model.bean;

import com.awsd.personnel.*;

import com.esdnl.complaint.model.bean.*;
import com.esdnl.complaint.model.constant.*;

import java.util.*;
import java.text.*;

public class ComplaintCommentBean 
{
  private int id;
  private ComplaintBean complaint;
  private Date submittedDate;
  private Personnel madeBy;
  private String comment;
  
  public ComplaintCommentBean()
  {
    this.id = 0;
    this.complaint =null;
    this.submittedDate = null;
    this.madeBy = null;
    this.comment = null;
  }
  
  public int getId()
  {
    return this.id;
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
  
  public Date getSubmittedDate()
  {
    return this.submittedDate;
  }
  
  public String getFormatedSubmittedDate()
  {
    if(this.submittedDate == null)
      return null;
      
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    
    return sdf.format(this.submittedDate);
  }
  
  public void setSubmittedDate(Date submittedDate)
  {
    this.submittedDate = submittedDate;
  }
  
  public Personnel getMadeBy()
  {
    return this.madeBy;
  }
  
  public void setMadeBy(Personnel madeBy)
  {
    this.madeBy = madeBy;
  }
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String comment)
  {
    this.comment = comment;
  }
}