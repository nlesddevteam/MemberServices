package com.esdnl.personnel.v2.site.recognition.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;

import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.database.recognition.*;
import com.esdnl.personnel.v2.model.sds.bean.*;

public class TemplatesTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private TemplateBean value;
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
  
  public void setValue(TemplateBean value)
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
      TemplateBean[] beans = TemplateManager.getTemplateBeans();
      out = pageContext.getOut();
      
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
       if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT TEMPLATE</OPTION>");
      for(int i=0; i < beans.length; i++)
      {
        out.println("<OPTION VALUE='" + beans[i].getId() +"'" + (((this.value != null) && this.value.equals(beans[i]))?" SELECTED":"") + ">" + beans[i].getName() + "</OPTION>");
      }
      out.println("</SELECT>");
    }
    catch(RecognitionException e)
    {
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}