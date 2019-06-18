package com.esdnl.complaint.site.tag;


import com.esdnl.util.StringUtils;
import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.complaint.database.*;
import com.esdnl.complaint.model.constant.*;
import com.esdnl.complaint.model.bean.*;

import com.awsd.personnel.*;

public class ComplaintHandlersTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private int value;
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
  
  public void setValue(int value)
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
    Personnel[] handlers = null;
    try
    {    
      handlers = ComplaintManager.getComplaintHandlers();
      
      out = pageContext.getOut();
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(" >");
      
      out.println("<OPTION VALUE=\"-1\">SELECT COMPLAINT HANDLER</OPTION>");
      for(int i = 0; i <handlers.length; i++)
        out.println("<OPTION VALUE=\"" + handlers[i].getPersonnelID() + "\""
          + ((handlers[i].getPersonnelID()==this.value)?" SELECTED":"") + ">" 
          + handlers[i].getFullName() + "</OPTION>");
      
      out.println("</SELECT>");
    }
    catch(ComplaintException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}