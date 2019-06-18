package com.esdnl.complaint.site.tag;

import com.esdnl.util.StringUtils;
import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.complaint.model.constant.*;
import com.esdnl.complaint.model.bean.*;

public class PhoneTypeTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private PhoneType value;
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setCls(String cls)
  {
    this.cls = cls;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public void setValue(PhoneType value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    try
    {    
      out = pageContext.getOut();
      
      for(int i = 0; i <PhoneType.ALL.length; i++)
      {
        out.print("<input type='radio' value='"+ PhoneType.ALL[i].getValue() +"'" 
          + (PhoneType.ALL[i].equals(this.value)?" CHECKED ":" ") 
          + "id='"+ this.id +"' name='"+ this.id +"'");
        if((this.cls != null) && !this.cls.trim().equals(""))
          out.print(" class=\"" + this.cls + "\"");
        if((this.style != null) && !this.style.trim().equals(""))
          out.print(" style=\"" + this.style + "\"");
        out.println(">"+ PhoneType.ALL[i].getDescription() + (((i+1) < PhoneType.ALL.length)?"<BR>":""));     
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}