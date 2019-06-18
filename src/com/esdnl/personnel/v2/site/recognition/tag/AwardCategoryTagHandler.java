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

public class AwardCategoryTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private AwardCategoryBean value;
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
  
  public void setValue(AwardCategoryBean value)
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
      AwardCategoryBean[] cats = AwardCategoryManager.getAwardCategoryBean();
      out = pageContext.getOut();
      
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
       if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT AWARD CATEGORY</OPTION>");
      for(int i=0; i < cats.length; i++)
      {
        out.println("<OPTION VALUE='" + cats[i].getCategoryId() +"'" + (((this.value != null) && this.value.equals(cats[i]))?" SELECTED":"") + ">" + cats[i].getCategoryName() + "</OPTION>");
      }
      out.println("</SELECT>");
    }
    catch(EmployeeException e)
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