package com.esdnl.photocopier.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;
import com.esdnl.photocopier.bean.*;
import com.esdnl.photocopier.dao.*;

public class PhotocopierSupplierTagHandler  extends TagSupport
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
    SupplierBean[] beans = null;
    
    try
    {
      beans = SupplierManager.getSupplierBeans();
      
      out = pageContext.getOut();
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\""); 
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT SUPPLIER</OPTION>");
      for(int i = 0; i < beans.length; i++)
        out.println("<OPTION VALUE=\"" + beans[i].getId() + "\"" + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == beans[i].getId()))? " SELECTED":"") + ">" + beans[i] + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(PhotocopierException e)
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