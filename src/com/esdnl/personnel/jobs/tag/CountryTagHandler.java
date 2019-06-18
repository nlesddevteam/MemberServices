package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;

public class CountryTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String value;
  
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
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT COUNTRY</OPTION>");
      out.println("<OPTION VALUE='CA'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("CA"))?" SELECTED":"") + ">Canada</OPTION>");
      out.println("<OPTION VALUE='US'" + ((!StringUtils.isEmpty(this.value) && this.value.equals("US"))?" SELECTED":"") + ">United States</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}