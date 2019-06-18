package com.esdnl.webmaint.ceoweb.bean;

import java.util.*;
import com.esdnl.webmaint.ceoweb.constants.*;

public class MessageBean 
{
  private int msg_id;
  private int msg_type;
  private String msg;
  private String msg_img;
  private Date msg_date;
  private String msg_title;
  
  public MessageBean()
  {
    msg_id = -1;
    msg_type = -1;
    msg = null;
    msg_img = null;
    msg_date = null;
    msg_title = null;
  }
  
  public int getMessageID()
  {
    return this.msg_id;
  }
  
  public void setMessageID(int msg_id)
  {
    this.msg_id = msg_id;
  }
  
  public String getMessageTitle()
  {
    return this.msg_title;
  }
  
  public void setMessageTitle(String msg_title)
  {
    this.msg_title = msg_title;
  }
  
  public MessageTypeConstant getMessageType()
  {
    return MessageTypeConstant.get(this.msg_type);
  }
  
  public void setMessageType(int msg_type)
  {
    this.msg_type = msg_type;
  }
  
  public String getMessage()
  {
    return this.msg;
  }
  
  public void setMessage(String msg)
  {
    this.msg = msg;
  }
  
  public String getMessageImage()
  {
    return this.msg_img;
  }
  
  public void setMessageImage(String msg_img)
  {
    this.msg_img = msg_img;
  }
  
  public Date getMessageDate()
  {
    return this.msg_date;
  }
  
  public void setMessageDate(Date msg_date)
  {
    this.msg_date = msg_date;
  }
}