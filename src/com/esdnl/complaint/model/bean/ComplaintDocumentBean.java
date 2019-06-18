package com.esdnl.complaint.model.bean;

import com.awsd.personnel.*;

import com.esdnl.complaint.model.bean.*;
import com.esdnl.complaint.model.constant.*;

import java.util.*;
import java.text.*;

public class ComplaintDocumentBean 
{
  private int doc_id;
  private ComplaintBean complaint;
  private String title;
  private String filename;
  private Personnel uploadedBy;
  private Date uploadedDate;
  
  public ComplaintDocumentBean()
  {
    doc_id = 0;
    complaint = null;
    title = null;
    filename = null;
    uploadedBy = null;
    uploadedDate = null;
  }
  
  public int getId()
  {
    return this.doc_id;
  }
  
  public void setId(int doc_id)
  {
    this.doc_id = doc_id;
  }
  
  public ComplaintBean getComplaint()
  {
    return this.complaint;
  }
  
  public void setComplaint(ComplaintBean complaint)
  {
    this.complaint = complaint;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getFilename()
  {
    return this.filename;
  }
  
  public void setFilename(String filename)
  {
    this.filename = filename;
  }
  
  public Personnel getUploadedBy()
  {
    return this.uploadedBy;
  }
  
  public void setUploadedBy(Personnel uploadedBy)
  {
    this.uploadedBy = uploadedBy;
  }
  
  public Date getUploadedDate()
  {
    return this.uploadedDate;
  }
  
  public String getFormattedUploadDate()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    return sdf.format(this.uploadedDate);
  }
  
  public void setUploadedDate(Date uploadedDate)
  {
    this.uploadedDate = uploadedDate;
  }
}