package com.esdnl.personnel.v2.site.recognition.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;

import com.esdnl.personnel.v2.model.recognition.constant.RequestType;

public class RecognitionRequestClassTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private RequestType value;
  private String onChange;
  
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
  
  public void setValue(RequestType value)
  {
    this.value = value;
  }
  
  public void setOnChange(String onChange)
  {
    this.onChange = onChange;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    try
    {
      out = pageContext.getOut();
      
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
       if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT REQUEST CLASS</OPTION>");
      for(int i=0; i < RequestType.ALL.length; i++)
      {
        out.println("<OPTION VALUE='" + RequestType.ALL[i].getId() +"'" + (((this.value != null) && this.value.equals(RequestType.ALL[i]))?" SELECTED":"") + ">" + RequestType.ALL[i].getDescription() + "</OPTION>");
      }
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}