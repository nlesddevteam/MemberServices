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

public class ComplaintTypeTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private ComplaintType value;
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
  
  public void setValue(ComplaintType value)
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
      out.println(" >");
      
      out.println("<OPTION VALUE=\"-1\">SELECT CATEGORY</OPTION>");
      for(int i = 0; i <ComplaintType.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + ComplaintType.ALL[i].getValue() + "\""
          + ((ComplaintType.ALL[i].equals(this.value))?" SELECTED":"") + ">" 
          + ComplaintType.ALL[i].getDescription() + "</OPTION>");
      
      out.println("</SELECT>");
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}