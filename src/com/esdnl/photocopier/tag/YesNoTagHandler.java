package com.esdnl.photocopier.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class YesNoTagHandler   extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String value;
  private String onchange;
  
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
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public void setOnchange(String onchange)
  {
    this.onchange = onchange;
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
      if((this.onchange != null) && !this.onchange.trim().equals(""))
        out.print(" onchange=\"" + this.onchange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='0'" + (((this.value == null)||((this.value != null)&&this.value.equalsIgnoreCase("NO")))?" SELECTED":"") + ">NO</OPTION>");
      out.println("<OPTION VALUE='1'" + (((this.value != null)&&this.value.equalsIgnoreCase("YES"))?" SELECTED":"") + ">YES</OPTION>");
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}