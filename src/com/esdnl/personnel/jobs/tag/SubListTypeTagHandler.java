package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;

public class SubListTypeTagHandler extends TagSupport
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
      
      for(int i = 0; i < SubstituteListConstant.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + SubstituteListConstant.ALL[i].getValue() + "\""
          + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value)==SubstituteListConstant.ALL[i].getValue()))?" SELECTED":"") + ">" 
          + SubstituteListConstant.ALL[i].getDescription() + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}