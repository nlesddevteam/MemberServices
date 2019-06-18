package com.esdnl.personnel.v2.model.recognition.bean;

import com.esdnl.personnel.v2.model.recognition.constant.RequestStatus;
import com.esdnl.personnel.v2.model.recognition.constant.RequestType;
import com.awsd.personnel.*;

import java.util.*;
import com.awsd.personnel.Personnel;

public class RecognitionRequestBean
{
  private int Id;
  private RequestType type;
  private String description;
  private RequestStatus currentStatus;
  private String templateDocument;
  private Vector entities;
  private TreeMap history;
  private AwardTypeBean award;

  public RecognitionRequestBean()
  {
    this.Id = 0;
    this.type = null;
    this.description = null;
    this.currentStatus = null;
    this.templateDocument = null;
    this.award = null;
    
    entities = new Vector();
    
    history = null;
  }

  public int getId()
  {
    return Id;
  }

  public void setId(int newId)
  {
    Id = newId;
  }

  public RequestType getType()
  {
    return type;
  }

  public void setType(RequestType newType)
  {
    type = newType;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String newDescription)
  {
    description = newDescription;
  }

  public RequestStatus getCurrentStatus()
  {
    return currentStatus;
  }

  public void setCurrentStatus(RequestStatus newCurrentStatus)
  {
    currentStatus = newCurrentStatus;
  }

  public String getTemplateDocument()
  {
    return templateDocument;
  }

  public void setTemplateDocument(String newTemplateDocument)
  {
    templateDocument = newTemplateDocument;
  }
  
  public void addEntity(IEntity entity)
  {
    entities.add(entity);
  }
  
  public void addEntities(Collection c)
  {
    entities.addAll(c);
  }
  
  public Object[] getEntities()
  {
    return entities.toArray();
  }
  
  public TreeMap getHistory()
  {
    return this.history;
  }
  
  public void setHistory(TreeMap history)
  {
    this.history = history;
  }
  
  public void setAwardType(AwardTypeBean award)
  {
    this.award = award;
  }
  
  public AwardTypeBean getAwardType()
  {
    return this.award;
  }
  
  public String[] process()
  {
    Object[] arr = this.getEntities();
    String[] letters = null;
    TemplateBean template =  null;
    
    if(arr.length > 0)
    {
      letters = new String[arr.length];
      template = new TemplateBean();
      template.setText(this.templateDocument);
      
      for(int i=0; i < arr.length; i++)
      {
        letters[i] = template.process(arr[i]);
      }
    }
    
    System.out.println(letters.length);
    
    return letters;
  }

}