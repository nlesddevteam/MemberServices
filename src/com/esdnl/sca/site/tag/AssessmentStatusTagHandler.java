package com.esdnl.sca.site.tag;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.sca.dao.*;
import com.esdnl.sca.model.bean.*;

public class AssessmentStatusTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private AssessmentStatus value;
  private String multiple;
  
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
  
  public void setValue(AssessmentStatus value)
  {
    this.value = value;
  }
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
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
      if(Boolean.valueOf(multiple).booleanValue())
        out.println(" MULTIPLE");
      out.println(" >");
    
      for(int i=0; i < AssessmentStatus.ALL.length; i++)
      {
        out.println("<OPTION VALUE=\"" + AssessmentStatus.ALL[i].getId() + "\"" + (AssessmentStatus.ALL[i].equals(this.value)?" SELECTED":"") + ">" + AssessmentStatus.ALL[i].getDescription() + "</OPTION>");
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